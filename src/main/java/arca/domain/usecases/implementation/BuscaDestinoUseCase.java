package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.Localidade;
import arca.domain.entities.ResultListaLocalidade;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;

public class BuscaDestinoUseCase extends UseCase<BuscaDestinoUseCase.BuscaDestinoResult, BuscaDestinoUseCase.BuscaDestinoParams> {

    private final String method = "buscaDestino?origem=%s";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ResultListaLocalidade> parseJson;
    private final ConexaoOperadora conexaoOperadora;

    public BuscaDestinoUseCase(final RequestModel requestModel, final ParseJson<ResultListaLocalidade> parseJson, final ConexaoOperadora conexaoOperadora) {
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexaoOperadora = conexaoOperadora;
    }

    @Override
    public BuscaDestinoResult execute(final BuscaDestinoParams params) {
        try{
            return validate(requestModel.execute(conexaoOperadora, String.format(method,params.origem.id.toString() ), type));
        }catch (final NetworkException e){
            return new BuscaDestinoResult(e);
        }
    }
    private BuscaDestinoResult validate(final String json){
        try{
            return new BuscaDestinoResult(parseJson.parse(json));
        }catch (final ParseException pe){
            return new BuscaDestinoResult(pe);
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
