package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.ConsultaOnibus;
import arca.domain.entities.Error;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.ParseException;
import arca.logger.Logger;

public class BuscaOnibusUseCase extends UseCase<BuscaOnibusUseCase.BuscaOnibusResult, BuscaOnibusUseCase.BuscaOnibusParams> {

    private final String path = "buscaOnibus?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s";

    private final RequestModel requestModel;
    private final ParseJson<ConsultaOnibus> parseJson;
    private final ConexaoOperadora conexaoOperadora;
    private final Logger logger;

    public BuscaOnibusUseCase(final RequestModel requestModel,
                              final ParseJson<ConsultaOnibus> parseJson,
                              final ConexaoOperadora conexaoOperadora,
                              final Logger logger){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexaoOperadora = conexaoOperadora;
        this.logger = logger;
    }

    @Override
    public BuscaOnibusResult execute(final BuscaOnibusParams params) {
        try{
            final String url = String.format(path, params.origem.toString(), params.destino.toString(), params.data, params.servico, params.grupo);
           logger.add(url);
            return validate(requestModel.execute(conexaoOperadora, url, RequestModel.RequestType.GET));
        }catch (final Exception e){
            return new BuscaOnibusResult(e);
        }
    }

    private BuscaOnibusResult validate(final RequestModel.ResponseModel response){
        try {
            if(response.isSucess()){
                return new BuscaOnibusResult(parseJson.parse(response.body));
            }else{
                return new BuscaOnibusResult(response.error);
            }
        } catch (final ParseException pe) {
            return new BuscaOnibusResult(pe);
        }
    }

    public static class BuscaOnibusParams extends Params {
        public final Long origem;
        public final Long destino;
        public final String data;
        public final String servico;
        public final String grupo;

        public BuscaOnibusParams(final Long origem, final Long destino, final String data, final String servico, final String grupo) {
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.servico = servico;
            this.grupo = grupo;
        }
    }

    public static class BuscaOnibusResult extends Result<ConsultaOnibus> {
        public BuscaOnibusResult(final ConsultaOnibus result) {
            super(result);
        }
        public BuscaOnibusResult(final Exception exception) {
            super(exception);
        }
        public BuscaOnibusResult(final Error error) {
            super(error);
        }
    }

}
