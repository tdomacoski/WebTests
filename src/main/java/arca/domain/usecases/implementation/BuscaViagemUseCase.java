package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.util.DateUtils;

import java.util.Calendar;

public class BuscaViagemUseCase extends UseCase<BuscaViagemUseCase.BuscaViagemResult, BuscaViagemUseCase.BuscaViagemParams> {

    private final String method = "buscaCorrida?origem=%s&destino=%s&data=%s";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ConsultaServicos> parseJson;
    private final Conexao conexao;

    public BuscaViagemUseCase(final RequestModel requestModel, final ParseJson<ConsultaServicos> parseJson, final Conexao conexao){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexao = conexao;
    }

    @Override
    public BuscaViagemResult execute(final BuscaViagemParams params) {
        final String urlMethod =
                String.format(method, params.origem.id, params.destino.id, DateUtils.formatFromAPI(params.date.getTimeInMillis()));
        try{
            return validate( requestModel.execute(conexao, urlMethod, type) );
        }catch (final NetworkException ne){
            return new BuscaViagemResult(ne);
        }
    }

    private BuscaViagemResult validate(final String json){
        final ConsultaServicos consultaServicos = parseJson.parse(json);
        if(null == consultaServicos){
            return new BuscaViagemResult(new Exception(String.format("no parse json: %s", json)));
        }else{
            return new BuscaViagemResult(consultaServicos);
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
    }
}