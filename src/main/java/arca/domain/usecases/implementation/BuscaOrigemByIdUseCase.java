package arca.domain.usecases.implementation;

import arca.domain.entities.Localidade;
import arca.domain.usecases.None;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NoSearchResultException;

import java.util.List;

public class BuscaOrigemByIdUseCase extends UseCase<BuscaOrigemByIdUseCase.BuscaOrigemByIdResult, BuscaOrigemByIdUseCase.BuscaOrigemByIdParams> {


    private final BuscaOrigemUseCase buscaOrigemUseCase;

    public BuscaOrigemByIdUseCase(final BuscaOrigemUseCase buscaOrigemUseCase) {
        this.buscaOrigemUseCase = buscaOrigemUseCase;
    }


    @Override
    public BuscaOrigemByIdResult execute(BuscaOrigemByIdParams params) {

        final BuscaOrigemUseCase.BuscaOrigemResult result =
                buscaOrigemUseCase.execute(new None());
        if (result.isSuccess()) {
            final Localidade localidade = find(
                    result.result.listaLocalidade.lsLocalidade,
                    params.id);
            if (null != localidade) {
                return new BuscaOrigemByIdResult(localidade);
            } else {
                return new BuscaOrigemByIdResult(new NoSearchResultException());
            }
        } else {
            return new BuscaOrigemByIdResult(result.exception);
        }
    }

    private Localidade find(final List<Localidade> localidades, final Long id) {
        for (final Localidade localidade : localidades) {
            if (id.equals(localidade.id)) {
                return localidade;
            }
        }
        return null;
    }

    public static class BuscaOrigemByIdParams extends Params {
        public final Long id;

        public BuscaOrigemByIdParams(final Long id) {
            this.id = id;
        }
    }

    public static class BuscaOrigemByIdResult extends Result<Localidade> {
        public BuscaOrigemByIdResult(Localidade result) {
            super(result);
        }

        public BuscaOrigemByIdResult(Exception exception) {
            super(exception);
        }
    }
}
