package main;

import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.DevolvePoltrona;
import arca.domain.usecases.implementation.CancelarReservaUseCase;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.GsonUtil;

public class CancelarPoltrona {
    private static final Logger  logger = new LoggerFile();

    private static final ConexaoOperadora operadora =
            OperadoraIntegration.santAnjo().vendas;
    private static final RequestModel requestModel =
            RequestModelIntegration.getRequestModel();
    private static final CancelarReservaUseCase cancelarReserva =
            new CancelarReservaUseCase(requestModel, ParseJsonIntegration.getDevolvePoltronaToJson(), operadora, logger);


    public static void main(String[] args) {

        try{

            final CancelarReservaUseCase.CancelarReservaResult r =
                    cancelarReserva.execute(generate());
            if(r.isSuccess()){
                final DevolvePoltrona dp = r.result;
                final String dpStr  = GsonUtil.GSON.toJson(dp);
//                LoggerFile.debug("Cancelamento com sucesso: "+dpStr);
//                LoggerFile.trace(dpStr);
            }else{
                r.exception.printStackTrace();
            }
        }catch (final Exception e){
            System.out.println(e.getLocalizedMessage());
        }


    }


    private static final CancelarReservaUseCase.CancelarReservaParams generate(){

        return new CancelarReservaUseCase.CancelarReservaParams(
                "17412", "15921","2020-08-18",
                "27301","SANJO", "7f2e0da7a149439a10cd42214",
                "10000000787696","73", "0"
        );
    }
}
