
package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.*;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.DateUtils;
import arca.util.ThreadUtils;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ValideOperadora {

    private final Logger logger;
    private final Operadora operadora;
    private final String nomePassageiro = "ArcaTestPlataforms";
    private final String documentoPassageiro = "57645754";

    private final Long origem = 17686L;
    private final Long destino = 18697L;




    ValideOperadora(final Operadora operadora) {
        this.operadora = operadora;
        this.logger = new LoggerFile(String.format("operadora_%s", operadora.nome));
    }
    public static void main(String[] args) {
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
        System.out.println("Iniciando testes em "+operadora.nome);
        final ValideOperadora validate = new ValideOperadora( operadora );
        try {
//            validate.valideCompraOrigemDestino();
            validate.execute();
        }catch (final Exception e){
            validate.logger.error(e);
            e.printStackTrace();
        }
    }



    private void valideCompraOrigemDestino() throws Exception{
        final Calendar data = Calendar.getInstance();

        int day = 1;

        while(day != 15) {
            data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(day));

            day++;
            final Localidade origem = getOrigemById();
            final Localidade destino = getDestinoById(origem);

            Servico servico = null;
            Poltrona poltrona = null;

            servico = getServico(origem, destino, data);
            if (null == servico) {
                logger.add(String.format("Serviço para %s de %s para %s não encontrado",
                        DateUtils.formatFromAPI(data.getTimeInMillis()), origem.cidade, destino.cidade  ));
                continue;
            }
            poltrona = getPoltrona(origem, destino, servico, data);
            if (poltrona == null) {
                logger.add("Poltrona não encontrada. . . ");
                continue;
            }
            final BloquearPoltrona bloqueio = getBloqueio(origem, destino, data, servico, poltrona);
            if (null == bloqueio) {
                logger.add("Não foi posspivel bloquear a poltrona. . . ");
                continue;
            }
            final ConfirmacaoVenda confirmacao = getConfirma(origem, destino, data, servico,
                    bloqueio.bloqueioPoltrona);
            if (null == confirmacao) {
                logger.add("Não foi possível confirmar. . . ");
                continue;
            }
            logger.add("\n Compra realizada! \n");
            logger.add(confirmacao.toJson());
            logger.add("\n");

            final BuscaStatusBilhete status = buscaStatus(servico.grupo, servico.servico, confirmacao.numeroBilhete, origem.id.toString(),
                    destino.id.toString(), data, bloqueio.bloqueioPoltrona.assento);

            ThreadUtils.sleepOneSecond();

            DevolvePoltrona devolvePoltrona = getDevolve(origem, destino, data, servico,
                    bloqueio.bloqueioPoltrona, confirmacao);
//

            logger.add(String.format("> Origem: %s ", origem.toJson()));
            logger.add(String.format("> Destino: %s ", destino.toJson()));
            logger.add(String.format("> Serviço: %s ", servico.toJson()));
            logger.add(String.format("> Poltrona: %s ", poltrona.toJson()));
            logger.add(String.format("> Confirmação: %s ", confirmacao.toJson()));
            logger.add(String.format("> Devolução: %s ", devolvePoltrona.toJson()));
//            return;
        }


    }

    private void valideOrigemDestino() throws Exception {

        final Calendar data = Calendar.getInstance();
        int day = 1;
        data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(day));
        while(day != 15){
            day++;
            final Localidade cwb = getOrigemById();
            System.out.println(cwb.toJson());
            final Localidade pg = getDestinoById(cwb);
            System.out.println(pg.toJson());
            final ConsultaServicos servico = getConsultaServio(cwb, pg, data);
            System.out.println("\n");
            logger.add(servico.toJson());
            System.out.println("\n");
            ThreadUtils.sleepTreeSecond();
            data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(day));
        }

    }

    private void execute() throws Exception {
        Localidade origem = null;
        Localidade destino = null;
        Servico servico = null;
        Poltrona poltrona = null;

        final Calendar data = Calendar.getInstance();
        data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));

        while (true) {
            origem = getOrigem();
            destino = getDestino(origem);
            if (destino == null) {
                logger.add("Procurando destinos. . . ");
                continue;
            }
            servico = getServico(origem, destino, data);
            if (null == servico) {
                logger.add("Procurando serviços. . . ");
                continue;
            }
            System.out.println(servico.toJson());
            poltrona = getPoltrona(origem, destino, servico, data);
            if (poltrona == null) {
                logger.add("Procurando poltronas. . . ");
                continue;
            }
            final BloquearPoltrona bloqueio = getBloqueio(origem, destino, data, servico, poltrona);
            if (null == bloqueio) {
                continue;
            }
            final ConfirmacaoVenda confirmacao = getConfirma(origem, destino, data, servico,
                    bloqueio.bloqueioPoltrona);
            if (null == confirmacao) {
                continue;
            }
            logger.add("\n Compra realizada! \n");
            logger.add(confirmacao.toJson());
            logger.add("\n");

            final BuscaStatusBilhete status = buscaStatus(servico.grupo, servico.servico, confirmacao.numeroBilhete, origem.id.toString(),
                    destino.id.toString(), data, bloqueio.bloqueioPoltrona.assento);

            ThreadUtils.sleepOneSecond();

            DevolvePoltrona devolvePoltrona = getDevolve(origem, destino, data, servico,
                    bloqueio.bloqueioPoltrona, confirmacao);

            final BuscaStatusBilhete statusDevolucao = buscaStatus(servico.grupo, servico.servico, confirmacao.numeroBilhete, origem.id.toString(),
                    destino.id.toString(), data, bloqueio.bloqueioPoltrona.assento);

            logger.add(String.format("> Origem: %s ", origem.toJson()));
            logger.add(String.format("> Destino: %s ", destino.toJson()));
            logger.add(String.format("> Serviço: %s ", servico.toJson()));
            logger.add(String.format("> Poltrona: %s ", poltrona.toJson()));
            logger.add(String.format("> Confirmação: %s ", confirmacao.toJson()));
            logger.add(String.format("> Status Compra: %s ", status.toJson()));
            logger.add(String.format("> Devolução: %s ", devolvePoltrona.toJson()));
            logger.add(String.format("> Status Devolução: %s ", statusDevolucao.toJson()));
            break;
        }

    }


    private final Localidade getOrigemById() throws  Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        for(final Localidade localidade: localidades){
            if(origem.equals(localidade.id)){
                return localidade;
            }
        }
        return null;
    }


    private final Localidade getDestinoById(final Localidade origem) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getDestinos(operadora, logger, origem);
        if (localidades.isEmpty()) {
            return null;
        }
        for(final Localidade localidade: localidades){
            if(destino.equals(localidade.id)){
                return localidade;
            }
        }
        return null;
    }

    private final Localidade getOrigem() throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        int r = 0;
        final Random random = new Random();
        while (r <= 0) {
            r = random.nextInt();
        }
        final Localidade localidade = localidades.get((r % localidades.size()));
        if(localidade.uf.toUpperCase().contains("SC")){
            return getOrigem();
        }else{
            return localidade;
        }
    }


    private DevolvePoltrona getDevolve(final Localidade origem,
                                       final Localidade destino,
                                       final Calendar data,
                                       final Servico servico,
                                       final BloqueioPoltrona reserva,
                                       final ConfirmacaoVenda venda)throws Exception{
        return ApiIntegration.cancelarPoltrona(operadora, logger, origem,
                destino, data, servico, reserva, venda);
    }

    private BloquearPoltrona getBloqueio(final Localidade origem,
                                         final Localidade destino,
                                         final Calendar data,
                                         final Servico servico,
                                         final Poltrona poltrona)throws Exception{

        return ApiIntegration.reservarViagem(operadora, logger, origem, destino, data, servico,
                poltrona, URLEncoder.encode( nomePassageiro, "utf-8"), documentoPassageiro);
    }

    private ConfirmacaoVenda getConfirma(final Localidade origem,
                                         final Localidade destino, final Calendar data,
                                         final Servico servico, final BloqueioPoltrona reserva) throws Exception {
        return  ApiIntegration.confirmarReserva(operadora, logger, origem, destino, data, servico,
                reserva, nomePassageiro, documentoPassageiro);
    }

    private Poltrona getPoltrona(final Localidade origem, final Localidade destino, final Servico servico,
                                 final Calendar data) throws Exception {

        final ConsultaOnibus onibus = ApiIntegration.getOnibus(operadora, logger, origem, destino, data, servico);

        if (onibus == null) {
            return null;
        }

        if (onibus.onibus.mapaPoltrona.isEmpty()) {
            return null;
        }

        for (final Poltrona poltrona : onibus.onibus.mapaPoltrona) {
            if (poltrona.disponivel) {
                return poltrona;
            }
        }
        return null;
    }

    private final Servico getServico(final Localidade origem, final Localidade destino, final Calendar data) throws Exception {
        final ConsultaServicos servicos = ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, data);

        if (servicos.lsServicos.isEmpty()) {
            return null;
        } else {
            return servicos.lsServicos.get(0);
        }
    }

    private final ConsultaServicos getConsultaServio(final Localidade origem, final Localidade destino, final Calendar data) throws Exception {
        return  ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, data);
    }

    private final Localidade getDestino(final Localidade origem) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getDestinos(operadora, logger, origem);
        if (localidades.isEmpty()) {
            return null;
        }
        int r = 0;
        final Random random = new Random();
        while (r <= 0) {
            r = random.nextInt();
        }
        return localidades.get((r % localidades.size()));
    }


    private final BuscaStatusBilhete buscaStatus(final String grupo, final String servico, final String numBilhete,
                                                 final String origem, final String destino,
                                                 final Calendar data, final String poltrona) throws Exception {
        return  ApiIntegration.statuBilhete(operadora, logger, grupo, servico, numBilhete, origem, destino,
                DateUtils.formatFromAPI(data.getTimeInMillis()), poltrona);
    }


}
