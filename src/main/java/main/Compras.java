package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.*;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.ThreadUtils;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Compras {

    public Long id = null;
    public static void main(String[] args) {
        try {
            new Compras(OperadoraIntegration.uniao(), new LoggerFile("uniao"), "Thiago Domacoski", "68602521").exec();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private final Operadora operadora;
    private final Logger logger;
    final String nomePassageiro;
    final String documentoPassageiro;

    public Compras(final Operadora operadora, final Logger logger, final String nomePassageiro, final String documentoPassageiro) {
        this.operadora = operadora;
        this.logger = logger;
        this.nomePassageiro = nomePassageiro;
        this.documentoPassageiro = documentoPassageiro;
    }

    public final void exec() throws Exception {


        Localidade origem = null;
        Localidade destino = null;
        Servico servico = null;
        Poltrona poltrona = null;


        final Calendar data = Calendar.getInstance();
        data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));

        while (true) {
            origem =  id!= null ? getOrigem(id): getOrigem();
//            origem = getOrigemSC();
            if(origem == null){
                logger.add("Não possui SC!");
                return;
            }
            destino = getDestino(origem);
            if (destino == null) {
                continue;
            }
            servico = getServico(origem, destino, data);
            if (null == servico) {
                continue;
            }

            poltrona = getPoltrona(origem, destino, servico, data);

            if (poltrona == null) {
                continue;
            }

            final BloquearPoltrona bloqueio = getBloqueio(origem, destino, data, servico, poltrona);
            if(null == bloqueio){
                continue;
            }



//
//            final ConfirmacaoVenda confirmacao = getConfirma(origem, destino, data, servico,
//                    bloqueio.bloqueioPoltrona);
//            if(null == confirmacao){
//                continue;
//            }
//
//
//            ThreadUtils.sleepTreeSecond();
//
//            DevolvePoltrona devolvePoltrona = getDevolve(origem, destino, data, servico,
//                    bloqueio.bloqueioPoltrona, confirmacao);


            logger.add(String.format("> Origem: %s ", origem.toJson()));
            logger.add(String.format("> Destino: %s ", destino.toJson()));
//            logger.add(String.format("> Serviço: %s ", servico.toJson()));
            logger.add(String.format("> Poltrona: %s ", poltrona.toJson()));
//            logger.add(String.format("> Confirmação: %s ",confirmacao.toJson()));
//            logger.add(String.format("> Devolução: %s ",devolvePoltrona.toJson()));

            break;

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
                poltrona, nomePassageiro, documentoPassageiro);
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


    private final Localidade getOrigem(final Long id) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        for(final Localidade l : localidades){
            if(l.id.equals(id)){
                return l;
            }
        }
        return null;
    }


    private final Localidade getOrigemSC() throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        for (final Localidade localidade : localidades) {
                if("sc".equals(localidade.uf.toLowerCase())){
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
        return localidades.get((r % localidades.size()));
    }


}
