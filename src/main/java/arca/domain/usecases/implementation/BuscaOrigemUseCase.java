package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.ResultListaLocalidade;
import arca.domain.usecases.None;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;

public class BuscaOrigemUseCase extends UseCase<BuscaOrigemUseCase.BuscaOrigemResult, None> {

    private final String method = "buscaOrigem";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ResultListaLocalidade> parseJson;
    private final Conexao conexao;

    public BuscaOrigemUseCase(final Conexao conexao, final RequestModel requestModel, ParseJson<ResultListaLocalidade> parseJson){
        this.conexao = conexao;
        this.requestModel = requestModel;
        this.parseJson = parseJson;
    }

    @Override
    public BuscaOrigemResult execute(None params) {
        try {
            return validateAndTransform(
                    requestModel.execute(conexao, method, type)
            );
        }catch (final NetworkException e){
          return new BuscaOrigemResult(e);
        }
    }

    private BuscaOrigemResult validateAndTransform(final String json){
        final ResultListaLocalidade localidades = parseJson.parse(json);
        if(null == localidades){
            return new BuscaOrigemResult(new Exception(String.format("no parse json to object : %s", json)));
        }else{
            return new BuscaOrigemResult(localidades);
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
