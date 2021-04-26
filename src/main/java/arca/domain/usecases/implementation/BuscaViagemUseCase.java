package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Error;
import arca.domain.entities.*;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;
import arca.logger.Logger;
import arca.util.DateUtils;

import java.util.Calendar;

public class BuscaViagemUseCase extends UseCase<BuscaViagemUseCase.BuscaViagemResult, BuscaViagemUseCase.BuscaViagemParams> {

    private final String method = "buscaCorrida?origem=%s&destino=%s&data=%s";

    private final RequestModel requestModel;
    private final ParseJson<ResultadoViagem> parseJson;
    private final ConexaoOperadora conexaoOperadora;
    private final Logger logger;

    public BuscaViagemUseCase(final RequestModel requestModel,
                              final ParseJson<ResultadoViagem> parseJson,
                              final ConexaoOperadora conexaoOperadora,
                              final Logger logger){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexaoOperadora = conexaoOperadora;
        this.logger = logger;
    }

    @Override
    public BuscaViagemResult execute(final BuscaViagemParams params) {

        try{
            final String url = generateUrl(params);
            logger.add(String.format("%s%s", conexaoOperadora.url, url));
            return validate( requestModel.execute(conexaoOperadora, url, RequestModel.RequestType.GET) );
        }catch (final NetworkException ne){
            return new BuscaViagemResult(ne);
        }
    }
    private String generateUrl(final BuscaViagemParams params){
        final String urlMethod =
                String.format(method, params.origem.id, params.destino.id,
                        DateUtils.formatFromAPI(params.date.getTimeInMillis()));
        return urlMethod;
    }

    private BuscaViagemResult validate(final RequestModel.ResponseModel response){
        try{
            if(response.isSucess()){
                return new BuscaViagemResult(parseJson.parse(response.body).consultaServicos);
            }else{
                return new BuscaViagemResult(response.error);
            }
        }catch (final ParseException pe){
            return new BuscaViagemResult(pe);
        }
    }

    public static class BuscaViagemParams extends Params {
        public final Localidade origem;
        public final Localidade destino;
        public final Calendar date;
        public BuscaViagemParams(final Localidade origem, final Localidade destino, final Calendar date) {
            this.origem = origem;
            this.destino = destino;
            this.date = date;
        }
    }

    public static class BuscaViagemResult extends Result<ConsultaServicos> {
        public BuscaViagemResult(ConsultaServicos result) { super(result); }
        public BuscaViagemResult(Exception exception) { super(exception); }
        public BuscaViagemResult(final Error error) { super(error); }
    }
}