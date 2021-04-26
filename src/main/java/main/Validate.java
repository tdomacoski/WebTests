package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.database.pojo.CancelamentoBilhete;
import arca.database.query.cancelamento.QueryCancelamentoByOrder;
import arca.domain.entities.DevolvePoltrona;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.GsonUtil;
import org.slf4j.ILoggerFactory;

import java.util.Iterator;
import java.util.List;

public class Validate {

    public static void main(String[] args) {

        try{
            final Logger logger = new LoggerFile("teste");
            final QueryCancelamentoByOrder queryCancelamentoByOrder = new QueryCancelamentoByOrder();

            final List<CancelamentoBilhete> list = queryCancelamentoByOrder.execute(
                    12135l);


            for(final CancelamentoBilhete c : list){
                if(c.bilhete == null){
                    continue;
                }
               System.out.println(GsonUtil.GSON.toJson(c));
                final Operadora operadora = OperadoraIntegration.byId(Long.valueOf(c.operadora));
                final DevolvePoltrona dp = ApiIntegration.cancelarPoltrona(operadora, logger, c.origem,
                        c.destino, c.data, c.servico, operadora.grupo, c.transacao_id, c.bilhete,
                        c.poltrona);
                System.out.println(dp.toJson());
                logger.add(dp.toJson());
            }

        }catch (final Exception e){
            e.printStackTrace();
        }
    }
}
