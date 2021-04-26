package arca.ci;

import arca.domain.entities.Operadora;
import arca.domain.usecases.implementation.*;
import arca.logger.Logger;

public class UseCaseIntegration {

    public static BuscaLocalidadeByIdUseCase getLocalidadeById(final Operadora operadora,
                                                               final Logger logger){
        return new BuscaLocalidadeByIdUseCase(getBuscaOrigem(operadora, logger));
    }

    public static BuscaOrigemUseCase getBuscaOrigem(final Operadora operadora,
                                                    final Logger logger){
       return new BuscaOrigemUseCase(operadora.vendas,
               RequestModelIntegration.getRequestModel(),
               ParseJsonIntegration.getResultListaLocalidade(), logger);
    }

    public static BuscaDestinoUseCase getBuscaDestino(final Operadora operadora,
                                                      final Logger logger){
        return new BuscaDestinoUseCase(RequestModelIntegration.getRequestModel(),
                ParseJsonIntegration.getResultListaLocalidade(),
                operadora.vendas, logger);
    }

    public static BuscaViagemUseCase getBuscaViagem(final Operadora operadora,
                                                    final Logger logger){
        return new BuscaViagemUseCase(RequestModelIntegration.getRequestModel(),
                ParseJsonIntegration.getParseResultadoViagem(), operadora.vendas, logger);
    }

    public static BuscaOnibusUseCase getBuscaOnibus(final Operadora operadora,
                                                    final Logger logger){
        return new BuscaOnibusUseCase(RequestModelIntegration.getRequestModel(),
                ParseJsonIntegration.getConsultaOnibusToJson(), operadora.vendas, logger);
    }

    public static ReservaViagemUseCase getReservaViagem(final Operadora operadora,
                                                         final Logger logger){
          return   new ReservaViagemUseCase(RequestModelIntegration.getRequestModel(),
                  ParseJsonIntegration.getBloquearPoltrona(), operadora.vendas, logger);
    }

    public static ConfirmaReservaUseCase getConfirmaReserva(final Operadora operadora,
                                                         final Logger logger) {
      return new ConfirmaReservaUseCase(RequestModelIntegration.getRequestModel(),
              ParseJsonIntegration.getConfirmacaoVendaResult(), operadora.vendas, logger);
    }
    public static CancelarReservaUseCase getCancelaReserva(final Operadora operadora,
                                                            final Logger logger) {
        return new CancelarReservaUseCase(RequestModelIntegration.getRequestModel(),
                ParseJsonIntegration.getDevolvePoltronaToJson(), operadora.vendas, logger);

    }

    public  static BuscaOrigemPorNomeUseCase getOrigemPorNome(final Operadora operadora,
                                                              final Logger logger){
        return new BuscaOrigemPorNomeUseCase(getBuscaOrigem(operadora, logger));
    }

    public static BuscaDestinoPorNomeUseCase getDestinoPorNome(final Operadora operadora,
                                                                final Logger logger){
        return new BuscaDestinoPorNomeUseCase(getBuscaDestino(operadora, logger));
    }

}
