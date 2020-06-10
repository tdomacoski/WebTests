package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.Localidade;
import arca.domain.entities.ResultListaLocalidade;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;

public class BuscaDestinoUseCase extends UseCase<BuscaDestinoUseCase.BuscaDestinoResult, BuscaDestinoUseCase.BuscaDestinoParams> {

    private final String method = "buscaDestino?origem=%s";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ResultListaLocalidade> parseJson;
    private final Conexao conexao;

    public BuscaDestinoUseCase(final RequestModel requestModel, final ParseJson<ResultListaLocalidade> parseJson, final Conexao conexao) {
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexao = conexao;
    }

    @Override
    public BuscaDestinoResult execute(final BuscaDestinoParams params) {
        try{
            return validate(requestModel.execute(conexao, String.format(method,params.origem.id.toString() ), type));
        }catch (final NetworkException e){
            return new BuscaDestinoResult(e);
        }
    }
    private BuscaDestinoResult validate(final String json){
        final ResultListaLocalidade result = parseJson.parse(json);
        if(null == result){
            return new BuscaDestinoResult(new Exception(String.format("no parse to json: %s", json)));
        }else{
            return new BuscaDestinoResult(result);
        }
    }

    public static class BuscaDestinoParams extends Params {
        public final Localidade origem;

        public BuscaDestinoParams(final Localidade origem) {
            this.origem = origem;
        }
    }

    public static class BuscaDestinoResult extends Result<ResultListaLocalidade> {
        public BuscaDestinoResult(ResultListaLocalidade result) {
            super(result);
        }

        public BuscaDestinoResult(Exception exception) {
            super(exception);
        }
    }
}
