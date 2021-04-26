package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.DateUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RotasGarcia {

    public static void main(String[] args) {

        new RotasGarcia(OperadoraIntegration.garcia()).execute();
    }

    private final Operadora operadora;
    private final Logger logger;
    private  final Logger clean = new LoggerFile("a_novas_rotas ouro");

    public RotasGarcia(final Operadora operadora) {
        this.operadora = operadora;
        this.logger = new LoggerFile(operadora.nome + "__rotas");
    }


    private void execute() {

        try {

            for (final Rotas rota : rotas()) {

                final Localidade origem = findOrigem(rota.origem);

                if (origem == null) {
                    clean.add(String.format("⛔ Origem não localizada [%s]", rota.origem.toString()));
                    continue;
                }

                final Localidade destino = findDestino(rota.destino, origem);

                if (destino == null) {
                    clean.add(String.format("⛔ Destino de %s , %s (%s) não localizado  [%s]", origem.cidade, origem.uf,
                            origem.id.toString(), rota.destino.toString()));
                    continue;
                }


                int count = 0;
                int day = 1;
                while (day != 8) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(day));
                    day++;
                    final String strData = DateUtils.ddMMyy.format(calendar.getTimeInMillis());
                    ConsultaServicos servicos = consultaServicos(origem, destino, calendar);
                    if (null == servicos || servicos.lsServicos.isEmpty()) {
                        logger.add(String.format("⛔ Nenhum serviço encontrado de %s / %s para %s / %s em %s",
                                origem.cidade, origem.uf, destino.cidade, destino.uf, strData));
                    } else {
                        logger.add(String.format("De %s / %s para %s / %s em %s contém %s",
                                origem.cidade, origem.uf, destino.cidade, destino.uf, strData, servicos.lsServicos.size() + ""));
                        count += servicos.lsServicos.size();
                    }
                }
                if(count == 0){
                    clean.add(String.format("⛔ Nenhum serviço encontrado de %s/%s para %s/%s",
                            origem.cidade, origem.uf, destino.cidade, destino.uf));
                }else{
                    clean.add(String.format("⭐ De %s/%s para %s/%s encontrou %s servicos",
                            origem.cidade, origem.uf, destino.cidade, destino.uf, (""+count)));

                }
            }
        } catch (final Exception e) {
            logger.error(e);
        }

    }

    private List<Rotas> rotas() {
        final List<Rotas> rotas = new ArrayList<>(0);

        try (BufferedReader br = Files.newBufferedReader(Paths.get("garcia_rotas.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] ids = line.split(",");
                rotas.add(new Rotas(Long.valueOf(ids[0]), Long.valueOf(ids[1])));
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }


        return rotas;
    }


    private ConsultaServicos consultaServicos(final Localidade origem, final Localidade destino, final Calendar data) throws Exception {
        return ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, data);
    }


    private Localidade findDestino(final Long id, final Localidade origem) throws Exception {
        final List<Localidade> localidades = ApiIntegration.
                getDestinos(operadora, logger, origem);
        for (final Localidade l : localidades) {
            if (l.id.equals(id)) {
                return l;
            }
        }
        return null;
    }

    private Localidade findOrigem(final Long id) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        for (final Localidade l : localidades) {
            if (l.id.equals(id)) {
                return l;
            }
        }
        return null;
    }


    static class Rotas {
        public final Long origem;
        public final Long destino;

        Rotas(final Long origem, final Long destino) {
            this.origem = origem;
            this.destino = destino;
        }
    }

}
