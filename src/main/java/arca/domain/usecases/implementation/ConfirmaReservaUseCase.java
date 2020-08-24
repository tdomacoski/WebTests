package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.ConfirmacaoVendaResult;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;
import arca.logger.Logger;

public class ConfirmaReservaUseCase extends UseCase<ConfirmaReservaUseCase.ReservaResult, ConfirmaReservaUseCase.ReservaParams> {
    private final String path = "confirmaVenda?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s&idtransacao=%s&documentoPassageiro=%s&seguro=%s&numAutorizacao=%s&numParcelas=%s&nomePassageiro=%s&descontoPercentual=%s&numFidelidade=%s&embarqueId=%s&desembarquei=%s";
    private final String type = "POST";

    private final RequestModel requestModel;
    private final ParseJson<ConfirmacaoVendaResult> parseJson;
    private final ConexaoOperadora conexaoOperadora;
    private final Logger logger;

    public ConfirmaReservaUseCase(final RequestModel requestModel,
                                  final ParseJson<ConfirmacaoVendaResult> parseJson,
                                  final ConexaoOperadora conexaoOperadora,
                                  final Logger logger){
        this.requestModel = requestModel;
        this.parseJson  = parseJson;
        this.conexaoOperadora = conexaoOperadora;
        this.logger = logger;
    }

    @Override
    public ReservaResult execute(ReservaParams params) {
        try {
            final String url = generateUrl(params);
            logger.add(url);
            return validate(
              requestModel.execute(conexaoOperadora, url, type)
            );
        } catch (final NetworkException ne) {
            return new ReservaResult(ne);
        }
    }

    private ReservaResult validate(final String json) {
        logger.add(json);
        try {
            return new ReservaResult(parseJson.parse(json));
        } catch (final ParseException pe) {
            return new ReservaResult(pe);
        }
    }

    private String generateUrl(final ReservaParams params){
        return String.format(path, params.origem, params.destino, params.data, params.servico,
                params.grupo, params.idtransacao, params.documentoPassageiro, params.seguro,
                params.numAutorizacao, params.numParcelas,
//                params.localizador,
                params.nomePassageiro,
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

