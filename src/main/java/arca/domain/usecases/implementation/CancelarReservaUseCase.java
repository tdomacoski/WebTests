package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.DevolvePoltrona;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;

public class CancelarReservaUseCase extends UseCase<CancelarReservaUseCase.CancelarReservaResult, CancelarReservaUseCase.CancelarReservaParams> {

    private final String method = "devolvePoltrona?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s&idtransacao=%s&numBilhete=%s&poltrona=%s&lancaMulta=%s";
    private  final String type = "PUT";
    private final RequestModel requestModel;
    private final ParseJson<DevolvePoltrona> parseJson;
    private final Conexao conexao;
    public CancelarReservaUseCase(final RequestModel requestModel, final ParseJson<DevolvePoltrona> parseJson,
                                  final Conexao conexao){
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexao = conexao;
    }

    @Override
    public CancelarReservaResult execute(CancelarReservaParams params) {
        try{
            return validate( requestModel.execute(conexao, generateUrl(params), type) );
        }catch (final NetworkException ne){
            return new CancelarReservaResult(ne);
        }
    }


    private CancelarReservaResult validate(final String json){
        final DevolvePoltrona result = parseJson.parse(json);
        if(null == json || "".equals(json)){
            return new CancelarReservaResult(new Exception(String.format("no parse json: %s", json)));
        }else{
            return new CancelarReservaResult(result);
        }
    }

    private String generateUrl(final CancelarReservaParams params){
        return String.format(method, params.origem, params.destino, params.data,
                params.servico, params.grupo, params.grupo, params.idtransacao,
                params.numBilhete, params.poltrona, params.lancaMulta);
    }


    public static class CancelarReservaParams extends Params{
        public final String origem;
        public final String destino;
        public final String data;
        public final String servico;
        public final String grupo;
        public final String idtransacao;
        public final String numBilhete;
        public final String poltrona;
        public final String lancaMulta;
        public CancelarReservaParams(final String origem, final String destino,final String data,
                final String servico, final String grupo, final String idtransacao, final String numBilhete,
                                     final String poltrona, final String lancaMulta){
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.servico = servico;
            this.grupo = grupo;
            this.idtransacao = idtransacao;
            this.numBilhete = numBilhete;
            this.poltrona = poltrona;
            this.lancaMulta = lancaMulta;
        }
    }
    public static class CancelarReservaResult extends Result<DevolvePoltrona>{
        public CancelarReservaResult(DevolvePoltrona result) {
            super(result);
        }
        public CancelarReservaResult(Exception exception) {
            super(exception);
        }
    }
}
