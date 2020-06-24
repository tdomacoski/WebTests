package main;

import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.*;
import arca.domain.usecases.None;
import arca.domain.usecases.implementation.*;
import arca.util.DateUtils;
import arca.util.Logger;
import arca.util.ThreadUtils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final String nomePassageiro = "Arca%20Test%20Plataform";
    private static final String documentoPassageiro = "123321231";
    private static final String seguro = "1";
    private static final String numAutorizacao = "556677";
    private static final String numParcelas = "2";
    private static final String localizador = "ARCASOLTEC";
    private static final String descontoPercentual = "50";
    private static final String numFidelidade = "123456789";
    private static final String lancaMulta = "0";

    private static final ConexaoOperadora garcia = OperadoraIntegration.garcia().vendas;
    private static final RequestModel requestModel =
            RequestModelIntegration.getRequestModel();
    private static final ParseJson<ResultListaLocalidade> parseLocalidades =
            ParseJsonIntegration.getResultListaLocalidade();
    private static final BuscaOrigemUseCase buscaOrigem =
            new BuscaOrigemUseCase(garcia, requestModel, parseLocalidades);
    private static final BuscaDestinoUseCase buscaDestino =
            new BuscaDestinoUseCase(requestModel, parseLocalidades, garcia);
    private static final BuscaViagemUseCase buscaViagem =
            new BuscaViagemUseCase(requestModel, ParseJsonIntegration.getParseResultadoViagem(), garcia);
    private static final BuscaOnibusUseCase buscaOnibus =
            new BuscaOnibusUseCase(requestModel, ParseJsonIntegration.getConsultaOnibusToJson(), garcia);
    private static final ReservaViagemUseCase reservaViagem =
            new ReservaViagemUseCase(requestModel, ParseJsonIntegration.getBloquearPoltrona(), garcia);
    private static final ConfirmaReservaUseCase confirmaReserva =
            new ConfirmaReservaUseCase(requestModel, ParseJsonIntegration.getConfirmacaoVendaResult(), garcia);
    private static final CancelarReservaUseCase cancelarReserva =
            new CancelarReservaUseCase(requestModel, ParseJsonIntegration.getDevolvePoltronaToJson(), garcia);


    public static void main(String[] args) {
//        debugMultiTrechos();
        validarOperadora();

    }

    public static void validarOperadora() {
        final List<Localidade> origens = getOrigens();
        final Calendar date = Calendar.getInstance();
        date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + 7));
        final String dateStr = DateUtils.formatFromAPI(date.getTimeInMillis());
        for (final Localidade origem : origens) {
            final List<Localidade> destinos = getDestinos(origem);
            for (final Localidade destino : destinos) {
                final BuscaViagemUseCase.BuscaViagemResult viagem = buscaViagem.execute(
                        new BuscaViagemUseCase.BuscaViagemParams(origem, destino, date)
                );

                if (!viagem.isSuccess()) {
                    continue;
                }
                if (viagem.result.lsServicos == null || viagem.result.lsServicos.size() <= 0) {
                   Logger.debug(String.format("Não há serviço %s para %s", origem.cidade, destino.cidade));
                    continue;
                }
                final Servico servico = viagem.result.lsServicos.get(0);
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

                if(!confirmar.isSuccess()){
                    continue;
                }

                if(confirmar.result.confirmacaoVenda == null){
                    continue;
                }
                final ConfirmacaoVenda confirmacaoVenda = confirmar.result.confirmacaoVenda;

                final CancelarReservaUseCase.CancelarReservaResult cancelarResult  =
                        cancelarReserva.execute(
                               new CancelarReservaUseCase.CancelarReservaParams(
                                    origem.id.toString(), destino.id.toString(), dateStr,
                                       servico.servico, servico.grupo, poltronaReservada.idTransacao,
                                       confirmacaoVenda.numeroBilhete, poltrona.numero, lancaMulta
                               )
                        );

                if(cancelarResult.isSuccess()){
                    System.out.println("Cancelamento realizado com sucesso!");
                    System.exit(0);
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
                System.out.println(servico);
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

}
