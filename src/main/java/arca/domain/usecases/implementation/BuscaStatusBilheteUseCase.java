package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;
import arca.logger.Logger;

public class BuscaStatusBilheteUseCase extends UseCase<BuscaStatusBilheteUseCase.BuscaStatusBilheteResult, BuscaStatusBilheteUseCase.BuscaStatusBilheteParams> {

    private final String method = "buscaStatusBilhete?grupo=%s&servico=%s&numBilhete=%s&origem=%s&destino=%s&data=%s&poltrona=%s";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<BuscaStatusBilhete> parseJson;
    private final ConexaoOperadora conexaoOperadora;
    private final Logger logger;

    public BuscaStatusBilheteUseCase(final RequestModel requestModel,
                                     final ParseJson<BuscaStatusBilhete> parseJson,
                                     final ConexaoOperadora conexaoOperadora,
                                     final Logger logger){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexaoOperadora = conexaoOperadora;
        this.logger = logger;
    }

    @Override
    public BuscaStatusBilheteResult execute(final BuscaStatusBilheteParams params) {
        try{
            final String url = generateUrl(params);
            logger.add(url);
            return validate( requestModel.execute(conexaoOperadora, url, type) );
        }catch (final NetworkException ne){
            return new BuscaStatusBilheteResult(ne);
        }
    }

    private String generateUrl(final BuscaStatusBilheteParams params){
        return String.format(method, params.grupo, params.servico, params.numBilhete,
                params.origem, params.destino, params.data, params.poltrona);
    }

    private BuscaStatusBilheteResult validate(final String json){
        logger.add(json);
        try{
            return new BuscaStatusBilheteResult(parseJson.parse(json));
        }catch (final ParseException pe){
            return new BuscaStatusBilheteResult(pe);
        }
    }

    public static class BuscaStatusBilheteParams extends Params {
        public final String grupo, servico,numBilhete, origem,destino,data,poltrona;
        public BuscaStatusBilheteParams(final String grupo, final String servico,
                                        final String numBilhete, final String origem,
                                        final String destino, final String data,
                                        final String poltrona) {
            this.grupo = grupo;
            this.servico = servico;
            this.numBilhete = numBilhete;
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.poltrona = poltrona;
        }
    }

    public static class BuscaStatusBilheteResult extends Result<BuscaStatusBilhete> {
        public BuscaStatusBilheteResult(BuscaStatusBilhete result) {
            super(result);
        }
        public BuscaStatusBilheteResult(Exception exception) {
            super(exception);
        }
    }
}
