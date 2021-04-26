package arca.ci;

import arca.domain.entities.*;
import arca.domain.usecases.None;
import arca.domain.usecases.implementation.*;
import arca.logger.Logger;
import arca.util.DateUtils;

import javax.print.DocFlavor;
import java.util.Calendar;
import java.util.List;

public class ApiIntegration {

    public static final List<Localidade> getOrigens(final Operadora operadora,
                                                    final Logger logger)throws Exception{
        final BuscaOrigemUseCase.BuscaOrigemResult r =
                UseCaseIntegration.getBuscaOrigem(operadora, logger).
                        execute(new None());
        if(r.isSuccess()){
            return r.result.listaLocalidade.lsLocalidade;
        }else{
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final List<Localidade> getDestinos(final Operadora operadora,
                                                     final Logger logger,
                                                     final Localidade origem) throws Exception{
        final BuscaDestinoUseCase.BuscaDestinoResult r =
                UseCaseIntegration.getBuscaDestino(operadora, logger)
                        .execute(new BuscaDestinoUseCase.BuscaDestinoParams(origem));
        if(r.isSuccess()){
            return r.result.listaLocalidade.lsLocalidade;
        }else{
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final Localidade getOrigemByName(final Operadora operadora,
                                                   final Logger logger,
                                                   final String cidade) throws Exception {
        final BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeResult r =
                UseCaseIntegration.getOrigemPorNome(operadora, logger).
                        execute(new BuscaOrigemPorNomeUseCase.
                        BuscaOrigemPorNomeParams(cidade));
        if (r.isSuccess()) {
            return r.result;
        }else{
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final Localidade getDestinoByName(final Operadora operadora,
                                              final Logger logger,
                                              final Localidade localidade,
                                              final String cidade) throws Exception {
        final BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeResult r =
                UseCaseIntegration.getDestinoPorNome(operadora, logger).
                        execute(new BuscaDestinoPorNomeUseCase.
                                BuscaDestinoPorNomeParams(localidade, cidade));
        if (!r.isSuccess()) {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        } else {
            return r.result;
        }
    }

    public static final ConsultaServicos getBuscaViagem(final Operadora operadora,
                                                        final Logger logger,
                                                        final Localidade origem,
                                                        final Localidade destino,
                                                        final Calendar data) throws Exception {
        final BuscaViagemUseCase.BuscaViagemResult r =
                UseCaseIntegration.getBuscaViagem(operadora, logger).execute(
                new BuscaViagemUseCase.BuscaViagemParams(origem, destino, data));
        if (!r.isSuccess()) {
            if(null != r.exception)
            throw r.exception;
            else
                throw new Exception(r.error.toJson());
        } else {
            return r.result;
        }
    }

    public static final ConsultaOnibus getOnibus(final Operadora operadora,
                                           final Logger logger,
                                           final Localidade origem,
                                           final Localidade destino,
                                           final Calendar data,
                                           final Servico servico) throws Exception {
        final BuscaOnibusUseCase.BuscaOnibusResult r =
                UseCaseIntegration.getBuscaOnibus(operadora, logger).execute(
                new BuscaOnibusUseCase.BuscaOnibusParams(origem.id, destino.id,
                        DateUtils.formatFromAPI(data.getTimeInMillis()),
                        servico.servico, servico.grupo));
        if (r.isSuccess()) {
            return  r.result;
        } else {
             if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final ConsultaOnibus getOnibus(final Operadora operadora,
                                                 final Logger logger,
                                                 final Localidade origem,
                                                 final Localidade destino,
                                                 final String data,
                                                 final Servico servico) throws Exception {
        final BuscaOnibusUseCase.BuscaOnibusResult r =
                UseCaseIntegration.getBuscaOnibus(operadora, logger).execute(
                        new BuscaOnibusUseCase.BuscaOnibusParams(origem.id, destino.id,
                                data, servico.servico, servico.grupo));
        if (!r.isSuccess()) {
            throw r.exception;
        } else {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final ConsultaOnibus getOnibusConexao(final Operadora operadora,
                                                 final Logger logger,
                                                 final Localidade origem,
                                                 final Localidade destino,
                                                 final Calendar data,
                                                 final String servico,
                                                        final String grupo) throws Exception {
        final BuscaOnibusUseCase.BuscaOnibusResult r =
                UseCaseIntegration.getBuscaOnibus(operadora, logger).execute(
                        new BuscaOnibusUseCase.BuscaOnibusParams(origem.id, destino.id,
                                DateUtils.formatFromAPI(data.getTimeInMillis()),
                                servico, grupo));
        if (!r.isSuccess()) {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        } else {
            return r.result;
        }
    }

    public static final BloquearPoltrona reservarViagem(final Operadora operadora,
                                                  final Logger logger,
                                                  final Localidade origem,
                                                  final Localidade destino,
                                                  final Calendar data,
                                                  final Servico servico,
                                                  final Poltrona poltrona,
                                                  final String nomePassageiro,
                                                  final String documentoPassageiro) throws Exception {
        final ReservaViagemUseCase.ReservaViagemResult r =
                UseCaseIntegration.getReservaViagem(operadora, logger)
                .execute(new ReservaViagemUseCase.ReservaViagemParams(
                        origem.id, destino.id, DateUtils.formatFromAPI(data.getTimeInMillis()),
                        servico.servico, servico.grupo, poltrona.numero, nomePassageiro, documentoPassageiro));
        if (r.isSuccess()) {
            return r.result;
        } else {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final ConfirmacaoVenda confirmarReserva(final Operadora operadora,
                                                    final Logger logger,
                                                    final Localidade origem,
                                                    final Localidade destino,
                                                    final Calendar data,
                                                    final Servico servico,
                                                    final BloqueioPoltrona reserva,
                                                    final String nomePassageiro,
                                                    final String documentoPassageiro) throws Exception {
        final ConfirmaReservaUseCase.ReservaResult r =
                UseCaseIntegration.getConfirmaReserva(operadora, logger).execute(
                new ConfirmaReservaUseCase.ReservaParams(
                        origem.id.toString(), destino.id.toString(),
                        DateUtils.formatFromAPI(data.getTimeInMillis()),
                        servico.servico, servico.grupo, reserva.idTransacao,
                        documentoPassageiro, "1", "556677", "2",
                        "ARCASOLTEC", nomePassageiro,
                        "0", "123456789",
                        origem.id.toString(), destino.id.toString()
                ));
        if (r.isSuccess()) {
            return r.result.confirmacaoVenda;
        } else {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final DevolvePoltrona cancelarPoltrona(final Operadora operadora,
                                                         final Logger logger,
                                                         final String origem,
                                                         final String destino,
                                                         final String data,
                                                         final String servico,
                                                         final String grupo,
                                                         final String idTransacao,
                                                         final String numeroBilhete,
                                                         final String assento) throws Exception {

        final CancelarReservaUseCase.CancelarReservaResult r =
                UseCaseIntegration.getCancelaReserva(operadora, logger).
                        execute(new CancelarReservaUseCase.CancelarReservaParams(
                                origem,
                                destino,
                                data,
                                servico,
                                grupo,
                                idTransacao,
                                numeroBilhete, assento, "0"));
        if (r.isSuccess()) {
            return r.result;
        } else {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }




    public static final DevolvePoltrona cancelarPoltrona(final Operadora operadora,
                                                         final Logger logger,
                                                         final Localidade origem,
                                                         final Localidade destino,
                                                         final Calendar data,
                                                         final Servico servico,
                                                         final BloqueioPoltrona reserva,
                                                         final ConfirmacaoVenda venda) throws Exception {

        final CancelarReservaUseCase.CancelarReservaResult r =
                UseCaseIntegration.getCancelaReserva(operadora, logger).
                        execute(new CancelarReservaUseCase.CancelarReservaParams(
                                origem.id.toString(), destino.id.toString(), DateUtils.formatFromAPI(data.getTimeInMillis()),
                                servico.servico, servico.grupo, reserva.idTransacao,
                                venda.numeroBilhete, reserva.assento, "0"));
        if (r.isSuccess()) {
            return r.result;
        } else {
            if(r.error != null){
                throw new Exception(r.error.toJson());
            }else{
                throw r.exception;
            }
        }
    }

    public static final BuscaStatusBilhete statuBilhete(final Operadora operadora,
                                                        final Logger logger,
                                                        final String grupo,
                                                        final String servico,
                                                        final String numBilhete,
                                                        final String origem,
                                                        final String destino,
                                                        final String data,
                                                        final String poltrona) throws  Exception{
        final BuscaStatusBilheteUseCase useCase =
                new BuscaStatusBilheteUseCase( RequestModelIntegration.getRequestModel(),
                        ParseJsonIntegration.getBuscaStatusBilheteToJson(),
                        operadora.vendas, logger);
        final BuscaStatusBilheteUseCase.BuscaStatusBilheteResult result =
                useCase.execute( new BuscaStatusBilheteUseCase.BuscaStatusBilheteParams(
                grupo, servico, numBilhete, origem, destino, data, poltrona) );
        if(result.isSuccess()){
            return result.result;
        }else{
            if(result.error != null){
                throw new Exception(result.error.toJson());
            }else{
                throw result.exception;
            }
        }


    }

}
