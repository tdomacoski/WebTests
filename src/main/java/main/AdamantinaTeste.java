package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.DateUtils;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdamantinaTeste {

    private Rota[] verdeTransportes = {
            new Rota(248l, 255l),
            new Rota(248l, 268l),
            new Rota(256l, 261l),
            new Rota(193l, 184l),
            new Rota(1l, 229l),
            new Rota(153l, 239l),
            new Rota(236l, 315l),
            new Rota(236l, 248l),
            new Rota(193l, 184L )
    };

    private final Operadora operadora = OperadoraIntegration.adamantina();
    private final Logger logger = new LoggerFile(String.format("Validacao_%s", operadora.nome));

    public static void main(String[] args) {
        try {


            new AdamantinaTeste().run();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    private void printLocalidades() throws Exception{
        for(final Localidade l : ApiIntegration.getOrigens(operadora, logger)){
            System.out.println(l.toJson());
        }
    }

    private void run() throws Exception {

        for (final Rota rota : verdeTransportes) {
            final Localidade origem = getOrigem(rota.origem);
            if (null == origem) {
                logger.add(String.format("Não localizado origem: %s", rota.origem));
            } else {
                final Localidade destino = getDestino(origem, rota.destino);
                if (null == destino) {
                    logger.add(String.format("%s não localizado com destino de %s", rota.destino, rota.origem));
                } else {
                    searchService(origem, destino);
                }
            }
        }
    }

    private void searchService(final Localidade origem, final Localidade destino) throws Exception {
        int day = 1;
        Calendar data = Calendar.getInstance();
        while (day != 11) {
            day++;
            data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(day));
            final ConsultaServicos servico =
                    ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, data);
            if (servico.lsServicos.isEmpty()) {
                logger.add(String.format("%s %s em %s sem serviços ", origem.cidade, destino.cidade,
                        DateUtils.formatFromAPI(data.getTimeInMillis())));
            } else {
                logger.add(String.format("%s %s encontrou %s serviços", origem.cidade, destino.cidade,
                        servico.lsServicos.size() + ""));
                logger.add(servico.toJson());
            }
        }


    }


    private Localidade getOrigem(final Long origem) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        for (final Localidade l : localidades) {
            if (origem.equals(l.id)) {
                return l;
            }
        }
        return null;
    }

    private Localidade getDestino(final Localidade origem, final Long destino) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getDestinos(operadora, logger, origem);
        for (final Localidade l : localidades) {
            if (destino.equals(l.id)) {
                return l;
            }
        }
        return null;
    }


    public static class Rota {
        final Long origem;
        final Long destino;

        public Rota(final Long origem, final Long destino) {
            this.origem = origem;
            this.destino = destino;
        }
    }
}
