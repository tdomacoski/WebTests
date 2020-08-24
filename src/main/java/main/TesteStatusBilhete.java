package main;

import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.JsonModel;
import arca.domain.usecases.implementation.BuscaStatusBilheteUseCase;
import arca.logger.Logger;
import arca.util.GsonUtil;
import arca.logger.LoggerFile;
import arca.util.ThreadUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TesteStatusBilhete {
    private  static final Logger logger = new LoggerFile();
    private static final String GRUPO = "PRINC";
    private static final ConexaoOperadora operadora =
            OperadoraIntegration.princesaDosCampos().vendas;

    private static final RequestModel requestModel =
            RequestModelIntegration.getRequestModel();
    private static final ParseJson<BuscaStatusBilhete> parseStatusBilhete =
            ParseJsonIntegration.getBuscaStatusBilheteToJson();
    private static final BuscaStatusBilheteUseCase  buscaStatusBilheteUseCase =
            new BuscaStatusBilheteUseCase(requestModel, parseStatusBilhete, operadora, logger );



    public static void main(String[] args) {

        final ListaPrincesa listaPrincesa = loadValues();
        for(final Bilhete bilhete: listaPrincesa.bilhetes){
            final BuscaStatusBilheteUseCase.BuscaStatusBilheteResult r =
            buscaStatusBilheteUseCase.execute(fromBilhete(bilhete));

            if(r.isSuccess()){
                final BuscaStatusBilhete status = r.result;
                LoggerFile.debug(status.toJson());
            }else{
                LoggerFile.debug("Não foi possível localizar o bilhete!");
            }
            ThreadUtils.sleepTreeSecond();
        }
    }


    private static BuscaStatusBilheteUseCase.BuscaStatusBilheteParams fromBilhete(
            final Bilhete b){
        return  new BuscaStatusBilheteUseCase.BuscaStatusBilheteParams(
                b.grupo, b.servico, b.numBilhete,b.origem, b.destino,b.data, b.poltrona);
    }


    private static ListaPrincesa loadValues(){
        final String json = readAllBytes("bilhetes_princesa.json");
        return GsonUtil.GSON.fromJson(json, ListaPrincesa.class);
    }

    private static String readAllBytes(final String filePath) {
        String content = "";
        try {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public class ListaPrincesa{
        public List<Bilhete> bilhetes;
    }

    public static class Bilhete extends JsonModel {
        public String grupo, servico, numBilhete, origem, destino, data, poltrona;
    }
}
