package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.database.pojo.StatusBilhete;
import arca.database.query.status.QueryStatusBilhete;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.util.List;

public class StatusBilhetePOesquisa {

    public static final Logger LOGGER = new LoggerFile("teste");


//    public static void main(String[] args)throws Exception {
//
//        final QueryStatusBilhete s = new QueryStatusBilhete();
//
//        for(final Long order: orders){
//            final List<StatusBilhete>  list = s.execute(order);
//            LOGGER.add("ORDER ID: "+order);
//            for(final StatusBilhete sb : list){
//                try {
//                    final BuscaStatusBilhete buscaStatusBilhete = consulta(sb);
//                    LOGGER.add(buscaStatusBilhete.toJson());
//                }catch (final Exception e){
//                    LOGGER.add(e.getMessage());
//                }
//            }
//        }
//    }

    public static BuscaStatusBilhete consulta(final StatusBilhete b) throws Exception {
        final Operadora operadora = OperadoraIntegration.byId(b.operadora);
        return ApiIntegration.statuBilhete(operadora, LOGGER, operadora.grupo, b.servico, b.numBilhete, b.origem, b.destino, b.data, b.poltrona);
    }
}
