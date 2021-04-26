package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.domain.entities.Servico;
import arca.logger.Logger;
import arca.logger.LoggerEmpty;
import arca.logger.LoggerFile;
import arca.util.DateUtils;
import arca.util.ThreadUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ValideGrupos {

    public static void main(String[] args) {
        try {
            final List<Operadora> operadoras = OperadoraIntegration.operadoras();
            System.out.println("Digite o número da Operadora:");
            for (int i = 0; i < operadoras.size(); i++) {
                System.out.println(String.format("[ %d ] %s", i, operadoras.get(i).nome));
            }

            Integer i = null;
            while (true) {
                Scanner in = new Scanner(System.in);
                System.out.print("Operadora: ");
                String s = in.nextLine();
                try {
                    i = Integer.valueOf(s);
                    break;
                } catch (final Exception e) {
                    System.out.println("Número inválido!");
                }
            }

            final Operadora operadora = operadoras.get(i);
            System.out.println(String.format("Iniciando a análise de grupos da %s", operadora.nome));
            new ValideGrupos(operadora).execute();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    final Logger logger = new LoggerEmpty();
    final Operadora operadora;
    final LoggerFile print;

    public ValideGrupos(final Operadora operadora) {
        this.operadora = operadora;
        this.print = new LoggerFile(this.operadora.nome);
    }

    private void execute() {
        try {
            print.add("Iniciando ");
            separarServicos();
            System.out.println(String.format("Resultado salvo em %s", print.getName()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void separarServicos() throws Exception {
        final HashMap<String, Logger> loggers = new HashMap<>(0);

        final List<Localidade> origens = ApiIntegration.getOrigens(operadora, logger);
        System.out.println("Total de localidades: " + origens.size());
        for (final Localidade origem : origens) {
                System.out.println(origem.toJson());
                final List<Localidade> destinos = ApiIntegration.getDestinos(operadora, logger, origem);
            System.out.println("Total de destinos  : " + destinos.size());
                for (final Localidade destino : destinos) {
                    int pt = 0;
                    Calendar calendar = Calendar.getInstance();
                    while (pt != 7) {
                        pt++;
                        calendar.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(pt));
                        final ConsultaServicos servicos = ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, calendar);
                        boolean achou = false;
                        for (final Servico servico : servicos.lsServicos) {
                            final String line = String.format(" %s (%s)  ROTA DE: %s(%s)  PARA: %s(%s) EM: %s",
                                    servico.empresa, servico.grupo, origem.cidade, origem.uf,
                                    destino.cidade, destino.uf, DateUtils.formatFromAPI(calendar.getTimeInMillis()));

                            Logger logger = null;
                            if (loggers.containsKey(servico.empresa)) {
                                logger = loggers.get(servico.empresa);
                            } else {
                                logger = new LoggerFile(servico.empresa);
                            }
                            logger.add(line);
                            achou = true;
                        }
                        if (achou) {
                            break;
                        }
                    }
                }
        }
    }

}