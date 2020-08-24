package main;

import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.*;
import arca.domain.usecases.None;
import arca.domain.usecases.implementation.*;
import arca.logger.Logger;
import arca.util.DateUtils;
import arca.util.GsonUtil;
import arca.logger.LoggerFile;
import arca.util.ThreadUtils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
    private  static final Logger logger = new LoggerFile();
    private static final String nomePassageiro = "Arca%20Test%20Plataform";
    private static final String documentoPassageiro = "123321231";
    private static final String seguro = "1";
    private static final String numAutorizacao = "556677";
    private static final String numParcelas = "2";
    private static final String localizador = "ARCASOLTEC";
    private static final String descontoPercentual = "50";
    private static final String numFidelidade = "123456789";
    private static final String lancaMulta = "0";

    private static final ConexaoOperadora operadora =
            OperadoraIntegration.garcia().vendas;

    private static final RequestModel requestModel =
            RequestModelIntegration.getRequestModel();
    private static final ParseJson<ResultListaLocalidade> parseLocalidades =
            ParseJsonIntegration.getResultListaLocalidade();
    private static final BuscaOrigemUseCase buscaOrigem =
            new BuscaOrigemUseCase(operadora, requestModel, parseLocalidades, logger);
    private static final BuscaDestinoUseCase buscaDestino =
            new BuscaDestinoUseCase(requestModel, parseLocalidades, operadora, logger);
    private static final BuscaViagemUseCase buscaViagem =
            new BuscaViagemUseCase(requestModel, ParseJsonIntegration.getParseResultadoViagem(), operadora, logger);
    private static final BuscaOnibusUseCase buscaOnibus =
            new BuscaOnibusUseCase(requestModel, ParseJsonIntegration.getConsultaOnibusToJson(), operadora, logger);
    private static final ReservaViagemUseCase reservaViagem =
            new ReservaViagemUseCase(requestModel, ParseJsonIntegration.getBloquearPoltrona(), operadora, logger);
    private static final ConfirmaReservaUseCase confirmaReserva =
            new ConfirmaReservaUseCase(requestModel, ParseJsonIntegration.getConfirmacaoVendaResult(), operadora, logger);
    private static final CancelarReservaUseCase cancelarReserva =
            new CancelarReservaUseCase(requestModel, ParseJsonIntegration.getDevolvePoltronaToJson(), operadora, logger);


    public static void main(String[] args) {
//      runBuscaMultiTrecho();
//        ","uf": "PR"},"destino": {"id": 19301,"cidade": "
//    validateCuritiba("LOANDA - PR", "CAMPINAS - SP");

        validarOperadora();
    }

    public static void validarOperadora() {
        final List<Localidade> origens = getOrigens();
        final Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + 7));
        final String dateStr = DateUtils.formatFromAPI(date.getTimeInMillis());
        LoggerFile.debug(String.format("Total de localidades: %d", origens.size()));
        for (final Localidade origem : origens) {
            LoggerFile.debug(String.format("%s (%s)", origem.cidade, origem.id.toString()));
            if (!origem.id.equals(12722L)) {
                continue;
            }

            LoggerFile.debug(String.format("Cidade de origem: %s", origem.cidade));
            final List<Localidade> destinos = getDestinos(origem);
            for (final Localidade destino : destinos) {
                final BuscaViagemUseCase.BuscaViagemResult viagem = buscaViagem.execute(
                        new BuscaViagemUseCase.BuscaViagemParams(origem, destino, date)
                );

                if (!viagem.isSuccess()) {
                    continue;
                }
                if (viagem.result.lsServicos == null || viagem.result.lsServicos.size() <= 0) {
                    LoggerFile.debug(String.format("Não há serviço %s para %s", origem.cidade, destino.cidade));
                    continue;
                }
                final Servico servico = viagem.result.lsServicos.get(0);

                LoggerFile.debug(servico.toJson());
                final BuscaOnibusUseCase.BuscaOnibusResult onibus =
                        buscaOnibus.execute(
                                new BuscaOnibusUseCase.BuscaOnibusParams(
                                        origem.id, destino.id, dateStr,
                                        servico.servico, servico.grupo));
                if (!onibus.isSuccess()) {
                    continue;
                }
                if (onibus.result.onibus.mapaPoltrona == null || onibus.result.onibus.mapaPoltrona.isEmpty()) {
                    continue;
                }
                Poltrona poltrona = null;
                for (Poltrona pol : onibus.result.onibus.mapaPoltrona) {
                    if (pol.disponivel) {
                        poltrona = pol;
                        break;
                    }
                }
                if (null == poltrona) {
                    continue;
                }

                final ReservaViagemUseCase.ReservaViagemResult reserva =
                        reservaViagem.execute(new ReservaViagemUseCase.ReservaViagemParams(
                                origem.id, destino.id, DateUtils.formatFromAPI(date.getTimeInMillis()),
                                servico.servico, servico.grupo, poltrona.numero, nomePassageiro, documentoPassageiro
                        ));

                if (!reserva.isSuccess()) {
                    continue;
                }
                final BloqueioPoltrona poltronaReservada = reserva.result.bloqueioPoltrona;

                ConfirmaReservaUseCase.ReservaResult confirmar = confirmaReserva.execute(
                        new ConfirmaReservaUseCase.ReservaParams(
                                origem.id.toString(), destino.id.toString(), dateStr,
                                servico.servico, servico.grupo, poltronaReservada.idTransacao,
                                documentoPassageiro, seguro, numAutorizacao, numParcelas,
                                localizador, nomePassageiro, descontoPercentual, numFidelidade,
                                origem.id.toString(), destino.id.toString()
                        )
                );

                if (!confirmar.isSuccess()) {
                    continue;
                }

                if (confirmar.result.confirmacaoVenda == null) {
                    continue;
                }
                final ConfirmacaoVenda confirmacaoVenda = confirmar.result.confirmacaoVenda;


                LoggerFile.debug(confirmacaoVenda.xmlBPE);
                LoggerFile.debug("Aguardando 33s para cancelar....");

                ThreadUtils.sleep(TimeUnit.SECONDS.toMillis(33));

                LoggerFile.debug("Iniciando cancelamento....");
                final CancelarReservaUseCase.CancelarReservaResult cancelarResult =
                        cancelarReserva.execute(
                                new CancelarReservaUseCase.CancelarReservaParams(
                                        origem.id.toString(), destino.id.toString(), dateStr,
                                        servico.servico, servico.grupo, poltronaReservada.idTransacao,
                                        confirmacaoVenda.numeroBilhete, poltrona.numero, lancaMulta
                                )
                        );

                if (cancelarResult.isSuccess()) {
                    System.out.println("Cancelamento realizado com sucesso!");
//                    System.exit(0);
                } else {
                    System.out.println(cancelarResult.exception);
                }


            }
        }
    }

    public static void runBuscaMultiTrecho() {
        final List<Pair<String, String>> trechos = new ArrayList<>(0);
        trechos.add(new Pair("LOANDA - PR", "CAMPINAS - SP"));
        trechos.add(new Pair("PRESIDENTE PRUDENTE - SP", "SAO PAULO TRBF - SP"));
        trechos.add(new Pair("FOZ DO IGUACU - PR", "RIBEIRAO PRETO - SP"));
        trechos.add(new Pair("CURITIBA - PR", "RIBEIRAO PRETO - SP"));
        trechos.add(new Pair("CURITIBA - PR", "UMUARAMA - PR"));
        trechos.add(new Pair("CURITIBA - PR", "-UMUARAMA - PR"));
        trechos.add(new Pair("CURITIBA - PR", "CAMPO GRANDE - MS"));
        final BuscaMultiTrechoUseCase multiTrechoUseCase = new BuscaMultiTrechoUseCase(
                new BuscaOrigemPorNomeUseCase(buscaOrigem),
                new BuscaDestinoPorNomeUseCase(buscaDestino),
                buscaViagem
        );
        final BuscaMultiTrechoUseCase.MultiTrechoResult result =
                multiTrechoUseCase.execute(new BuscaMultiTrechoUseCase.MultiTrechoParams(trechos));
        if (result.isSuccess()) {
            for (final Servico servico : result.result) {

                LoggerFile.debug(GsonUtil.GSON.toJson(servico));
                System.out.println(GsonUtil.GSON.toJson(servico));
            }
        } else {
            System.out.println(result.exception);
        }
    }

    private static final void debugMultiTrechos() {
        long ini = System.currentTimeMillis();
        final List<Localidade> origensTotal = getOrigens();
        final List<Localidade> origens = new ArrayList<>(0);
        int pt = 0;
        for (final Localidade localidade : origensTotal) {
            if (pt % 2 == 0) {
                origens.add(localidade);
            }
            pt++;
        }
        final Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + 5));
//        Logger.debug(String.format("Data consulta: %s", DateUtils.formatFromAPI(date.getTimeInMillis())));
//        Logger.debug(String.format("Total de origens: %d", origens.size()));
        final List<Servico> servicos = new ArrayList(0);
        for (final Localidade origem : origens) {
            final List<Localidade> destinos = getDestinos(origem);
//            Logger.debug(String.format("Total de destinos para %s: %d", origem.cidade, destinos.size()));
            for (final Localidade destino : destinos) {
                final BuscaViagemUseCase.BuscaViagemResult r = buscaViagem.execute(
                        new BuscaViagemUseCase.BuscaViagemParams(origem, destino, date)
                );
                ThreadUtils.sleep(333);
                if (null == r.result.lsServicos) {
                    continue;
                }
                for (final Servico servico : r.result.lsServicos) {
                    if (servico.grupoConexao != null) {
                        servicos.add(servico);
                    }
                }
            }
        }

        long total = (ini - System.currentTimeMillis());
//        Logger.debug(String.format("Total de conexões: %d", servicos.size()));
//        for (final Servico servico : servicos) {
//            Logger.debug(servico.toString());
//        }
//        Logger.debug(String.format("Tempo total de consulta: %s ", (TimeUnit.MINUTES.toMillis(total) + "")));
    }

    private static List<Localidade> getDestinos(final Localidade localidade) {
        final BuscaDestinoUseCase.BuscaDestinoResult result =
                buscaDestino.execute(new BuscaDestinoUseCase.BuscaDestinoParams(localidade));
        if (result.isSuccess()) {
            return result.result.listaLocalidade.lsLocalidade;
        } else {
//            Logger.debug(result.exception.getMessage());
            return new ArrayList(0);
        }
    }

    private static List<Localidade> getOrigens() {
        final BuscaOrigemUseCase.BuscaOrigemResult buscaOrigemResult =
                buscaOrigem.execute(new None());
        if (buscaOrigemResult.isSuccess()) {
            return buscaOrigemResult.result.listaLocalidade.lsLocalidade;
        } else {
//            Logger.debug(buscaOrigemResult.exception.getMessage());
            return new ArrayList<>(0);
        }
    }

    private static void validateCuritiba(final String origem, final String destino) {

        final BuscaOrigemPorNomeUseCase
                buscaOrigemPorNomeUseCase =
                new BuscaOrigemPorNomeUseCase(buscaOrigem);

        final BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeParams
                params =
                new BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeParams(origem);

        final BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeResult result =
                buscaOrigemPorNomeUseCase.execute(params);

        if (result.isSuccess()) {
            final Localidade localidadeOrigem = result.result;

            final BuscaDestinoPorNomeUseCase buscaDestinoPorNomeUseCase =
                    new BuscaDestinoPorNomeUseCase(buscaDestino);

            final BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeResult destinoResult =
                    buscaDestinoPorNomeUseCase.execute(
                            new BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeParams(
                                    localidadeOrigem,
                                    destino));

            if (destinoResult.isSuccess()) {

                final Localidade destinoLocalidade = destinoResult.result;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.MONTH, 8);
                calendar.set(Calendar.DAY_OF_MONTH, 2);
                LoggerFile.debug(String.format("de: %s, para: %s", localidadeOrigem.cidade, destinoLocalidade.cidade));
                send(localidadeOrigem, destinoLocalidade, Calendar.getInstance());

            } else {
                LoggerFile.debug(destinoResult.exception.toString());
            }


        } else {
            LoggerFile.debug(result.exception.getMessage());
        }


    }

    private static void send(final Localidade origem, final Localidade destino,
                             final Calendar date) {

        date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + 7));
        final String dateStr = DateUtils.formatFromAPI(date.getTimeInMillis());

        final BuscaViagemUseCase.BuscaViagemResult viagem = buscaViagem.execute(
                new BuscaViagemUseCase.BuscaViagemParams(origem, destino, date)
        );

        if (!viagem.isSuccess()) {
            LoggerFile.debug(viagem.exception.toString());
            return;
        }
        if (viagem.result.lsServicos == null || viagem.result.lsServicos.size() <= 0) {
            LoggerFile.debug(String.format("Não há serviço %s para %s", origem.cidade, destino.cidade));
            return;
        }
        final Servico servico = viagem.result.lsServicos.get(0);
        final BuscaOnibusUseCase.BuscaOnibusResult onibus =
                buscaOnibus.execute(
                        new BuscaOnibusUseCase.BuscaOnibusParams(
                                origem.id, destino.id, dateStr,
                                servico.servico, servico.grupo));
        if (!onibus.isSuccess()) {
            LoggerFile.debug(onibus.exception.toString());
            return;
        }
        if (onibus.result.onibus.mapaPoltrona == null || onibus.result.onibus.mapaPoltrona.isEmpty()) {
            LoggerFile.debug("mapaPoltrona esta vazia. . . . ");
            return;
        }
        Poltrona poltrona = null;
        for (Poltrona pol : onibus.result.onibus.mapaPoltrona) {
            if (pol.disponivel) {
                poltrona = pol;
                break;
            }
        }
        if (null == poltrona) {
            LoggerFile.debug("não existe poltrona!");
            return;
        }

//            final ReservaViagemUseCase.ReservaViagemResult reserva =
//                    reservaViagem.execute(new ReservaViagemUseCase.ReservaViagemParams(
//                            origem.id, destino.id, DateUtils.formatFromAPI(date.getTimeInMillis()),
//                            servico.servico, servico.grupo, poltrona.numero, nomePassageiro, documentoPassageiro
//                    ));
//
//            if (!reserva.isSuccess()) {
//                Logger.debug(reserva.exception.toString());
//                return;
//            }
//            final BloqueioPoltrona poltronaReservada = reserva.result.bloqueioPoltrona;
//
//            ConfirmaReservaUseCase.ReservaResult confirmar = confirmaReserva.execute(
//                    new ConfirmaReservaUseCase.ReservaParams(
//                            origem.id.toString(), destino.id.toString(), dateStr,
//                            servico.servico, servico.grupo, poltronaReservada.idTransacao,
//                            documentoPassageiro, seguro, numAutorizacao, numParcelas,
//                            localizador, nomePassageiro, descontoPercentual, numFidelidade,
//                            origem.id.toString(), destino.id.toString()
//                    )
//            );
//
//            if(!confirmar.isSuccess()){
//                Logger.debug(confirmar.exception.toString());
//                return;
//            }
//
//            if(confirmar.result.confirmacaoVenda == null){
//                Logger.debug("confirmar.result.confirmacaoVenda is null");
//                return;
//            }
//            final ConfirmacaoVenda confirmacaoVenda = confirmar.result.confirmacaoVenda;
//
//            Logger.debug("Aguardando 33s para cancelar....");
//
//            ThreadUtils.sleep(TimeUnit.SECONDS.toMillis(33));
//
//            Logger.debug("Iniciando cancelamento....");
//            final CancelarReservaUseCase.CancelarReservaResult cancelarResult  =
//                    cancelarReserva.execute(
//                            new CancelarReservaUseCase.CancelarReservaParams(
//                                    origem.id.toString(), destino.id.toString(), dateStr,
//                                    servico.servico, servico.grupo, poltronaReservada.idTransacao,
//                                    confirmacaoVenda.numeroBilhete, poltrona.numero, lancaMulta
//                            )
//                    );
//
//            if(cancelarResult.isSuccess()){
//                System.out.println("Cancelamento realizado com sucesso!");
////                    System.exit(0);
//            }else{
//                System.out.println(cancelarResult.exception);
//            }

    }
}
