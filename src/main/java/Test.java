import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.domain.entities.Localidade;
import arca.domain.usecases.None;
import arca.domain.usecases.implementation.BuscaDestinoUseCase;
import arca.domain.usecases.implementation.BuscaOrigemUseCase;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.GsonUtil;

public class Test {

private static final Logger LOGGER = new LoggerFile();
    public static void main(String[] args) {

        final BuscaOrigemUseCase buscaOrigemUseCase = new BuscaOrigemUseCase(
                OperadoraIntegration.garcia().vendas,
                RequestModelIntegration.getRequestModel(),
                ParseJsonIntegration.getResultListaLocalidade(), LOGGER );

        final BuscaOrigemUseCase.BuscaOrigemResult result =
                buscaOrigemUseCase.execute(new None());

        boolean sucess = result.isSuccess();
        if(sucess){
            Localidade curitiba = null;
            for(final Localidade origem: result.result.listaLocalidade.lsLocalidade){
                    if(origem.cidade.toLowerCase().contains("curitiba")){
                        curitiba = origem;
                        break;
                    }
            }
            if(null != curitiba){
                System.out.println(GsonUtil.GSON.toJson(curitiba));

                final BuscaDestinoUseCase.BuscaDestinoParams params = new BuscaDestinoUseCase.BuscaDestinoParams(curitiba);
                final BuscaDestinoUseCase buscaDestino = new BuscaDestinoUseCase(
                        RequestModelIntegration.getRequestModel(),
                        ParseJsonIntegration.getResultListaLocalidade(),
                        OperadoraIntegration.garcia().vendas, LOGGER);

                final BuscaDestinoUseCase.BuscaDestinoResult destinoResult =
                        buscaDestino.execute(params);

                if(destinoResult.isSuccess()){
                    for(final Localidade destino: destinoResult.result.listaLocalidade.lsLocalidade){
                        System.out.println( destino.toJson() );
                    }
                }
            }

        }
    }
}
