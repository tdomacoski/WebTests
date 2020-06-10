package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.ConsultaOnibus;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;

public class BuscaOnibusUseCase extends UseCase<BuscaOnibusUseCase.BuscaOnibusResult, BuscaOnibusUseCase.BuscaOnibusParams> {

    private final String path = "buscaOnibus?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s";
    private final String type = "GET";

    private final RequestModel requestModel;
    private final ParseJson<ConsultaOnibus> parseJson;
    private final Conexao conexao;

    public BuscaOnibusUseCase(final RequestModel requestModel, final ParseJson<ConsultaOnibus> parseJson, final Conexao conexao){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexao = conexao;
    }

    @Override
    public BuscaOnibusResult execute(final BuscaOnibusParams params) {
        try{
            final String url = String.format(path, params.origem.toString(), params.destino.toString(), params.data, params.servico, params.grupo);
            return validate(requestModel.execute(conexao, url, type));
        }catch (final Exception e){
            return new BuscaOnibusResult(e);
        }
    }

    private BuscaOnibusResult validate(final String json){
        final ConsultaOnibus onibus = parseJson.parse(json);
        if(null == onibus){
            return new BuscaOnibusResult(new Exception(String.format("no parse json: %s", json)));
        }else{
            return new BuscaOnibusResult(onibus);
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
    }

}
