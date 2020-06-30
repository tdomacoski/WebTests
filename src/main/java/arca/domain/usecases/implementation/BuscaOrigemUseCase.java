package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.ResultListaLocalidade;
import arca.domain.usecases.None;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;

public class BuscaOrigemUseCase extends UseCase<BuscaOrigemUseCase.BuscaOrigemResult, None> {

    private final String method = "buscaOrigem";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ResultListaLocalidade> parseJson;
    private final ConexaoOperadora conexaoOperadora;

    public BuscaOrigemUseCase(final ConexaoOperadora conexaoOperadora, final RequestModel requestModel, ParseJson<ResultListaLocalidade> parseJson){
        this.conexaoOperadora = conexaoOperadora;
        this.requestModel = requestModel;
        this.parseJson = parseJson;
    }

    @Override
    public BuscaOrigemResult execute(None params) {
        try {
            return validateAndTransform(
                    requestModel.execute(conexaoOperadora, method, type)
            );
        }catch (final NetworkException e){
          return new BuscaOrigemResult(e);
        }
    }

    private BuscaOrigemResult validateAndTransform(final String json){
        try{
            return new BuscaOrigemResult(parseJson.parse(json));
        }catch (final ParseException pe){
            return new BuscaOrigemResult(pe);
        }
    }

    public static class BuscaOrigemResult extends Result<ResultListaLocalidade>{
        public BuscaOrigemResult(ResultListaLocalidade result) {
            super(result);
        }
        public BuscaOrigemResult(Exception exception) {
            super(exception);
        }
    }
}
