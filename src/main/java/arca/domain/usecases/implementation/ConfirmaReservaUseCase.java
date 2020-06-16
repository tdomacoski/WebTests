package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.Conexao;
import arca.domain.entities.ConfirmacaoVendaResult;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;

public class ConfirmaReservaUseCase extends UseCase<ConfirmaReservaUseCase.ReservaResult, ConfirmaReservaUseCase.ReservaParams> {

    private final String path = "confirmaVenda?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s&idtransacao=%s&documentoPassageiro=%s&seguro=%s&numAutorizacao=%s&numParcelas=%s&localizador=%s&nomePassageiro=%s&descontoPercentual=%s&numFidelidade=%s&embarqueId=%s&desembarquei=%s";
    private final String type = "POST";

    private final RequestModel requestModel;
    private final ParseJson<ConfirmacaoVendaResult> parseJson;
    private final Conexao conexao;

    public ConfirmaReservaUseCase(final RequestModel requestModel,
                                  final ParseJson<ConfirmacaoVendaResult> parseJson,
                                  final Conexao conexao){
        this.requestModel = requestModel;
        this.parseJson  = parseJson;
        this.conexao = conexao;
    }

    @Override
    public ReservaResult execute(ReservaParams params) {
        try {
            return validate(
              requestModel.execute(conexao, generateUrl(params), type)
            );
        } catch (final NetworkException ne) {
            return new ReservaResult(ne);
        }
    }

    private ReservaResult validate(final String json){
        final ConfirmacaoVendaResult result = parseJson.parse(json);
        if(null == result){
            return new ReservaResult(new Exception(String.format("no parse json: %s", json)));
        }else{
            return new ReservaResult(result);
        }
    }

    private String generateUrl(final ReservaParams params){
        return String.format(path, params.origem, params.destino, params.data, params.servico,
                params.grupo, params.idtransacao, params.documentoPassageiro, params.seguro,
                params.numAutorizacao, params.numParcelas, params.localizador, params.nomePassageiro,
                params.descontoPercentual, params.numFidelidade, params.embarqueId, params.desembarquei);
    }


    public static class ReservaParams extends Params {
        private final String origem;
        private final String destino;
        private final String data;
        private final String servico;
        private final String grupo;
        private final String idtransacao;
        private final String documentoPassageiro;
        private final String seguro;
        private final String numAutorizacao;
        private final String numParcelas;
        private final String localizador;
        private final String nomePassageiro;
        private final String descontoPercentual;
        private final String numFidelidade;
        private final String embarqueId;
        private final String desembarquei;

        public ReservaParams(final String origem, final String destino, final String data,
                             final String servico, final String grupo, final String idtransacao, final String documentoPassageiro,
                             final String seguro, final String numAutorizacao, final String numParcelas,
                             final String localizador, final String nomePassageiro, final String descontoPercentual,
                             final String numFidelidade, final String embarqueId, final String desembarquei) {
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.servico = servico;
            this.grupo = grupo;
            this.idtransacao = idtransacao;
            this.documentoPassageiro = documentoPassageiro;
            this.seguro = seguro;
            this.numAutorizacao = numAutorizacao;
            this.numParcelas = numParcelas;
            this.localizador = localizador;
            this.nomePassageiro = nomePassageiro;
            this.descontoPercentual = descontoPercentual;
            this.numFidelidade = numFidelidade;
            this.embarqueId = embarqueId;
            this.desembarquei = desembarquei;
        }

    }

    public static class ReservaResult extends Result<ConfirmacaoVendaResult> {
        public ReservaResult(final ConfirmacaoVendaResult result) {
            super(result);
        }
        public ReservaResult(final Exception exception) {
            super(exception);
        }
    }
}

