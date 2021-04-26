package arca.domain.usecases.implementation;

import arca.domain.entities.Localidade;
import arca.domain.usecases.None;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NoSearchResultException;

import java.util.List;

public class BuscaLocalidadeByIdUseCase extends UseCase<BuscaLocalidadeByIdUseCase.LocalidadeByIdResult, BuscaLocalidadeByIdUseCase.LocalidadeByIdParams> {


    private final BuscaOrigemUseCase useCase;

    public BuscaLocalidadeByIdUseCase(final BuscaOrigemUseCase useCase){
        this.useCase = useCase;
    }


    @Override
    public LocalidadeByIdResult execute(final LocalidadeByIdParams params){
        try {
            return new LocalidadeByIdResult(findById(getLocalidades(), params.id));
        }catch (final Exception e){
            return new LocalidadeByIdResult(e);
        }
    }
    private final Localidade findById(final List<Localidade> localidades,
                                      final Long id)throws NoSearchResultException{

        for(final Localidade localidade: localidades){
            if(id.equals(localidade.id)){
                return localidade;
            }
        }
        throw new NoSearchResultException(String.format("Id: %s não localizado!", id.toString()));
    }

    private final List<Localidade> getLocalidades() throws Exception {
        final BuscaOrigemUseCase.BuscaOrigemResult r =
                useCase.execute(new None());

        if(r.isSuccess()){
            return r.result.listaLocalidade.lsLocalidade;
        }else{
            throw r.exception;
        }
    }


    public static final class LocalidadeByIdParams extends Params{
        private final Long id;
        public LocalidadeByIdParams(final Long id){
            this.id = id;
        }
    }
    public static final class LocalidadeByIdResult extends Result<Localidade>{
        public LocalidadeByIdResult(Localidade result) {
            super(result);
        }
        public LocalidadeByIdResult(Exception exception) {
            super(exception);
        }
    }

}
