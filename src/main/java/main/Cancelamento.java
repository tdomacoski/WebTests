package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.database.pojo.CancelamentoBilhete;
import arca.database.pojo.StatusBilhete;
import arca.database.query.cancelamento.QueryCancelamentoByOrder;
import arca.database.query.status.QueryStatusBilhete;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.DevolvePoltrona;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.GsonUtil;

import java.util.List;

public class Cancelamento {
    public static void main(String[] args) {
        try {
            new Cancelamento(44830l).execute();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }


    private final Long orderId;
    private final Logger logger;

    public Cancelamento(final Long orderId) {
        this.orderId = orderId;
        this.logger = new LoggerFile(String.format("cancelamento_%s", String.valueOf(orderId)));
    }

    public final void execute() throws Exception {
        realizarConsulta();
        realizarCancelamento();
        realizarConsulta();
//        from();
    }


    private void from()throws Exception{
        realizarCancelamento(popular());
    }

    private CancelamentoBilhete popular() {
        final CancelamentoBilhete bilhete = new CancelamentoBilhete();
        bilhete.ordem_id = "27097";
        bilhete.origem = "24548";
        bilhete.destino = "24549";
        bilhete.data = "2021-01-29";
        bilhete.servico = "8888";
        bilhete.operadora = "63";
        bilhete.bilhete = "10000015125872";
        bilhete.poltrona = "42";
        bilhete.transacao_id = "c58260ce82a07ffd481409af6";
        return bilhete;
    }

    private void realizarCancelamento(final CancelamentoBilhete bilhete) throws Exception {
        if (null == bilhete.bilhete) {
            logger.add(String.format(" Bilhete não emitido: %s", GsonUtil.GSON.toJson(bilhete)));
        } else {
            final DevolvePoltrona devolvePoltrona = cancelamento(bilhete);
            logger.add(devolvePoltrona.toJson());
        }
    }

    private void realizarCancelamento() throws Exception {
        System.out.println("cancelamento");
        final QueryCancelamentoByOrder queryCancelamento = new QueryCancelamentoByOrder();
        final List<CancelamentoBilhete> cancelamentos = queryCancelamento.execute(orderId);
        System.out.println(cancelamentos.size()+" PARA CANCELAR");
        for (final CancelamentoBilhete bilhete : cancelamentos) {
            if (null == bilhete.bilhete) {
                logger.add(String.format(" Bilhete não emitido: %s", GsonUtil.GSON.toJson(bilhete)));
            } else {
                    final DevolvePoltrona devolvePoltrona = cancelamento(bilhete);
                System.out.println("devolvePoltrona.toJson()\n\n");
                System.out.println(devolvePoltrona.toJson());
                    logger.add(devolvePoltrona.toJson());

            }
        }
    }

    private void realizarConsulta() throws Exception {
        final QueryStatusBilhete statusBilhete = new QueryStatusBilhete();
        final List<StatusBilhete> list = statusBilhete.execute(orderId);
        for (final StatusBilhete bilhete : list) {
            if (bilhete.numBilhete == null) {
                logger.add(String.format(" Bilhete não emitido: %s", GsonUtil.GSON.toJson(bilhete)));
            } else {
                final BuscaStatusBilhete buscaStatusBilhete = consultarStatus(bilhete);
                logger.add(buscaStatusBilhete.toJson());
            }
        }
    }

    public DevolvePoltrona cancelamento(final CancelamentoBilhete cancelamento) throws Exception {
        final Operadora operadora =  OperadoraIntegration.byId(Long.valueOf(cancelamento.operadora));
          return ApiIntegration.cancelarPoltrona(operadora, logger, cancelamento.origem,
                cancelamento.destino, cancelamento.data, cancelamento.servico, operadora.grupo,
                cancelamento.transacao_id, cancelamento.bilhete, cancelamento.poltrona);
    }

    public BuscaStatusBilhete consultarStatus(final StatusBilhete b) throws Exception {
        final Operadora operadora =  OperadoraIntegration.byId(b.operadora);
//
        return ApiIntegration.statuBilhete(operadora, logger, operadora.grupo, b.servico, b.numBilhete, b.origem, b.destino, b.data, b.poltrona);
    }


}
