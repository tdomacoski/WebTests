package arca.domain.usecases.implementation;

import arca.domain.entities.Localidade;
import arca.domain.entities.Servico;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.util.ThreadUtils;
import kotlin.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuscaMultiTrechoUseCase extends UseCase<BuscaMultiTrechoUseCase.MultiTrechoResult, BuscaMultiTrechoUseCase.MultiTrechoParams> {

    private final BuscaOrigemPorNomeUseCase buscaOrigem;
    private final BuscaDestinoPorNomeUseCase buscaDestino;
    private final BuscaViagemUseCase buscaViagem;

    public BuscaMultiTrechoUseCase(final BuscaOrigemPorNomeUseCase buscaOrigem,
                                   final BuscaDestinoPorNomeUseCase buscaDestino,
                                   final BuscaViagemUseCase buscaViagem) {
        this.buscaOrigem = buscaOrigem;
        this.buscaDestino = buscaDestino;
        this.buscaViagem = buscaViagem;
    }

    @Override
    public MultiTrechoResult execute(final MultiTrechoParams params) {
        try {
            final Calendar date = Calendar.getInstance();
            date.set(Calendar.DAY_OF_MONTH, (date.get(Calendar.DAY_OF_MONTH) + 5));
            final List<Servico> servicosMultiTrechos = new ArrayList<>(0);
            for (final Pair<String, String> trecho : params.trechos) {
                final Localidade origem = getOrigem(trecho.getFirst());
                final Localidade destino = getDestino(origem, trecho.getSecond());
                final List<Servico> servicos = getServicos(origem, destino, date);
                for(final Servico servico: servicos){
                    if(valideMultiTrecho(servico)){
                        servicosMultiTrechos.add(servico);
                    }
                }
                ThreadUtils.sleep(333); //cabalistic number!!!
            }
            return new MultiTrechoResult(servicosMultiTrechos);
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

    private List<Servico> getServicos(final Localidade origem, final Localidade destino, final Calendar date) throws Exception {
        final BuscaViagemUseCase.BuscaViagemResult result = buscaViagem.execute(
                new BuscaViagemUseCase.BuscaViagemParams(origem, destino, date)
        );
        if (result.isSuccess()) {
            if (null == result.result.lsServicos) {
                return new ArrayList<>(0);
            } else {
                return result.result.lsServicos;
            }
        } else {
            throw result.exception;
        }
    }

    private Localidade getDestino(final Localidade origem, final String name) throws Exception {
        final BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeResult result =
                buscaDestino.execute(
                new BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeParams(origem, name));
        if (result.isSuccess()) {
            if (null == result.result) {
                throw new NullPointerException(String.format("No search Localidade from %s", name));
            }else{
                return result.result;
            }
        } else {
            throw result.exception;
        }
    }

    private Localidade getOrigem(final String name) throws Exception {
        final BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeResult result = buscaOrigem.execute(
                new BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeParams(name)
        );
        if (result.isSuccess()) {
            if (null == result.result) {
                throw new NullPointerException(String.format("No search Localidade from %s", name));
            }
            return result.result;
        } else {
            throw result.exception;
        }
    }


    public static class MultiTrechoParams extends Params {
        public final List<Pair<String, String>> trechos;
        public MultiTrechoParams(List<Pair<String, String>> trechos) {
            this.trechos = trechos;
        }
    }

    public static class MultiTrechoResult extends Result<List<Servico>> {
        public MultiTrechoResult(List<Servico> result) {
            super(result);
        }
        public MultiTrechoResult(Exception exception) {
            super(exception);
        }
    }
}
