package main;

import arca.ci.OperadoraIntegration;
import arca.ci.ParseJsonIntegration;
import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.controllers.parse.ParseJson;
import arca.domain.entities.ConexaoOperadora;
import arca.domain.entities.ResultListaLocalidade;
import arca.domain.usecases.implementation.BuscaDestinoUseCase;
import arca.domain.usecases.implementation.BuscaOnibusUseCase;
import arca.domain.usecases.implementation.BuscaOrigemUseCase;
import arca.domain.usecases.implementation.BuscaViagemUseCase;
import arca.logger.Logger;
import arca.logger.LoggerFile;


public class TestAssentos {

    private  static final Logger logger = new LoggerFile();
    private static final ConexaoOperadora operadora =
            OperadoraIntegration.princesaDosCampos().vendas;

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


    public static void main(String[] args) {
//        testAssentos();
    }

    /**
     * Tem a finalidade de validar o numeros de assentos diponíveis na buscaCorrida
     * com os informados na BuscaOnibus
     */
//    private static void testAssentos() {
//
//        Calendar data = Calendar.getInstance();
//        data.setTimeInMillis((System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1)));
//
//        final String dataStr = DateUtils.formatFromAPI(data.getTimeInMillis());
//        System.out.println(dataStr);
//
//        final BuscaOrigemUseCase.BuscaOrigemResult result = buscaOrigem.execute(new None());
//        if (result.isSuccess()) {
//            final List<Localidade> origens = result.result.listaLocalidade.lsLocalidade;
//            for (final Localidade origem : origens) {
//                if(!origem.id.equals(12722l)){
//                    continue;
//                }
//                final BuscaDestinoUseCase.BuscaDestinoResult destinoResult =
//                        buscaDestino.execute(new BuscaDestinoUseCase.BuscaDestinoParams(origem));
//                if (destinoResult.isSuccess()) {
//
//                    final List<Localidade> destinos = destinoResult.result.listaLocalidade.lsLocalidade;
//
//                    for (final Localidade destino : destinos) {
//
//                        ThreadUtils.sleepOneSecond();
//                        final BuscaViagemUseCase.BuscaViagemResult buscaViagemResult =
//                                buscaViagem.execute(new BuscaViagemUseCase.BuscaViagemParams(
//                                        origem, destino, data));
//
//                        if (buscaViagemResult.isSuccess()) {
//                            final List<Servico> servicos = buscaViagemResult.result.lsServicos;
//                            for (final Servico servico : servicos) {
//                                ThreadUtils.sleepTreeSecond();
//
//                                final BuscaOnibusUseCase.BuscaOnibusResult buscaOnibusResult =
//                                        buscaOnibus.execute(new BuscaOnibusUseCase.BuscaOnibusParams(
//                                                origem.id, destino.id, dataStr,
//                                                servico.servico, servico.grupo
//                                        ));
//
//                                if (buscaOnibusResult.isSuccess()) {
//
//                                    final Onibus onibus = buscaOnibusResult.result.onibus;
//
//                                    int assentosLivres = 0;
//                                    for (final Poltrona poltrona : onibus.mapaPoltrona) {
//                                        if (poltrona.disponivel) {
//                                            assentosLivres++;
//                                        }
//                                    }
////                                    if (assentosLivres != servico.poltronasLivres) {
////
////                                    }else{
////                                        LoggerFile.debug(String.format("DE: %s (%s) PARA: %s (%s) DATA: %s buscaCorrida.poltronasLivres : %d buscaOnibus: %d",
////                                                origem.cidade, origem.id.toString(), destino.cidade, destino.id.toString(), dataStr, servico.poltronasLivres, assentosLivres));
////
////                                    }
//                                    ThreadUtils.sleepOneSecond();
//
//                                } else {
//                                    LoggerFile.debug(String.format("Onibus não encontrado: [%s para %s em %s]", origem.cidade, destino.cidade, dataStr));
//                                }
//                            }
//                        } else {
//                            LoggerFile.debug(String.format("Rota não encontrada: [%s para %s em %s]", origem.cidade, destino.cidade, dataStr));
//                        }
//                    }
//                } else {
//                    LoggerFile.debug(String.format("Não foi possível carregar destinos para : %s [ %s  ]", origem.cidade, result.exception.toString()));
//                }
//            }
//        } else {
//            LoggerFile.debug(String.format("Não foi possível encontrar origens ", result.exception.toString()));
//        }
//    }

}
