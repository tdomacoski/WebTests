package arca.domain.usecases.implementation;

import arca.ci.ApiIntegration;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.domain.entities.Servico;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.logger.Logger;
import arca.util.DateUtils;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class BuscaMultiTrechoUseCase extends UseCase<BuscaMultiTrechoUseCase.MultiTrechoResult, BuscaMultiTrechoUseCase.MultiTrechoParams> {


    private final Operadora operadora;
    private final Logger logger;

    public BuscaMultiTrechoUseCase(final Operadora operadora, final Logger logger) {
        this.operadora = operadora;
        this.logger = logger;
    }

    @Override
    public MultiTrechoResult execute(final MultiTrechoParams params) {
        try {
            int day = 0;
            while (day != 7) {
                day++;
                final Calendar date = Calendar.getInstance();
                date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + day));
                final Localidade origem = getOrigem(params.rota.origem);
                if (null == origem) {
                    logger.add(String.format("[!] Origem não encontrado [%s]", params.rota.origem.toString()));
                    return new MultiTrechoResult(new ConsultaServicos());
                }
                final Localidade destino = getDestino(origem, params.rota.destino);

                if (null == destino) {
                    logger.add(String.format("[!] Destino (%s) não encontrado para (%s)", params.rota.destino, params.rota.origem.toString()));
                    return new MultiTrechoResult(new ConsultaServicos());
                }

                final ConsultaServicos servicos = getServicos(origem, destino, date);
                if (null == servicos || servicos.lsServicos == null) {
                    logger.add(String.format("[!] Serviços não encontrado para %s ,  Origem :(%s), Destino (%s)",
                            DateUtils.formatFromAPI(date.getTimeInMillis()),
                            params.rota.origem.toString(),
                            params.rota.destino.toString()));
                } else {
                    for (final Servico s : servicos.lsServicos) {
                        if (valideMultiTrecho(s)) {
                            if (s.conexao.via.equals(params.rota.conexao.toString())) {
                                logger.add(String.format("() Trecho validado: Origem: %s, conexão: %s , Destino: %s, data: %s \n Result: %s "
                                        , params.rota.origem.toString(), params.rota.conexao.toString(), params.rota.destino.toString(),
                                        DateUtils.formatFromAPI(date.getTimeInMillis()), s.toJson()));
                            }
                        } else {
                            logger.add(String.format("[!] Serviços não apresenta multitrecho [ data: %s ,  Origem :(%s), Destino (%s) ]",
                                    DateUtils.formatFromAPI(date.getTimeInMillis()),
                                    params.rota.origem.toString(),
                                    params.rota.destino.toString()));
                        }
                    }
                }

            }
            return new MultiTrechoResult(new ConsultaServicos());
        } catch (final Exception e) {
            return new MultiTrechoResult(e);
        }
    }

    /**
     * se existe conexao, então é multi-trecho
     */
    private boolean valideMultiTrecho(final Servico servico) {
        if (null == servico) {
            return false;
        } else {
            return servico.conexao != null;
        }
    }

    private ConsultaServicos getServicos(final Localidade origem, final Localidade destino, final Calendar date) throws Exception {
        return ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, date);
    }

    private Localidade getDestino(final Localidade origem, final Long id) throws Exception {
        final List<Localidade> destinos = ApiIntegration.getDestinos(operadora, logger, origem);
        for (final Localidade d : destinos) {
            if (d.id.equals(id)) {
                return d;
            }
        }
        return null;
    }

    private Localidade getOrigem(final Long id) throws Exception {
        final List<Localidade> origens = ApiIntegration.getOrigens(operadora, logger);
        for (final Localidade l : origens) {
            if (l.id.equals(id)) {
                return l;
            }
        }
        return null;
    }

    public static class MultiTrechoParams extends Params {
        public final MultiRota rota;

        public MultiTrechoParams(final MultiRota rota) {
            this.rota = rota;
        }
    }

    public static class MultiTrechoResult extends Result<ConsultaServicos> {
        public MultiTrechoResult(ConsultaServicos result) {
            super(result);
        }

        public MultiTrechoResult(Exception exception) {
            super(exception);
        }
    }

    public static class MultiRota {
        public final Long origem, destino, conexao;

        public MultiRota(final Long origem, final Long conexao, final Long destino) {
            this.origem = origem;
            this.destino = destino;
            this.conexao = conexao;
        }
    }


}


