package arca.domain.usecases.implementation;

import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.BloquearPoltrona;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NetworkException;
import arca.exceptions.ParseException;

public class ReservaViagemUseCase extends UseCase<ReservaViagemUseCase.ReservaViagemResult, ReservaViagemUseCase.ReservaViagemParams> {

    private final String path = "bloquearPoltrona?origem=%s&destino=%s&data=%s&servico=%s&grupo=%s&poltrona=%s&nomePassageiro=%s&documentoPassageiro=%s";
    private final String type = "POST";

    private final RequestModel requestModel;
    private final ParseJson<BloquearPoltrona> parseJson;
    private final ConexaoOperadora conexaoOperadora;

    public ReservaViagemUseCase(final RequestModel requestModel, ParseJson<BloquearPoltrona> parseJson, final ConexaoOperadora conexaoOperadora) {
        this.requestModel = requestModel;
        this.parseJson = parseJson;
        this.conexaoOperadora = conexaoOperadora;
    }


    @Override
    public ReservaViagemResult execute(final ReservaViagemParams params) {
        try {
            return validate(
                    requestModel.execute(conexaoOperadora, generateUrl(params), type)
            );
        } catch (final NetworkException ne) {
            return new ReservaViagemResult(ne);
        }
    }

    private String generateUrl(final ReservaViagemParams params) {
        return String.format(path, params.origem.toString(), params.destino.toString(),
                params.data, params.servico, params.grupo, params.poltrona,
                params.nomePassageiro, params.documentoPassageiro);
    }

    private ReservaViagemResult validate(final String json) {
        try{
            return new ReservaViagemResult(parseJson.parse(json));
        }catch (final ParseException pe){
            return new ReservaViagemResult(pe);
        }
    }

    public static class ReservaViagemParams extends Params {
        private final Long origem;
        private final Long destino;
        private final String data;
        private final String servico;
        private final String grupo;
        private final String poltrona;
        private final String nomePassageiro;
        private final String documentoPassageiro;

        public ReservaViagemParams(final Long origem, final Long destino, final String data,
                                   final String servico, final String grupo, final String poltrona, final String nomePassageiro,
                                   final String documentoPassageiro) {
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.servico = servico;
            this.grupo = grupo;
            this.poltrona = poltrona;
            this.nomePassageiro = nomePassageiro;
            this.documentoPassageiro = documentoPassageiro;
        }
    }


    public static class ReservaViagemResult extends Result<BloquearPoltrona> {
        public ReservaViagemResult(BloquearPoltrona result) {
            super(result);
        }
        public ReservaViagemResult(Exception exception) {
            super(exception);
        }
    }


}
