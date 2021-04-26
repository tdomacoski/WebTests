package arca.domain.usecases.implementation;

import arca.domain.entities.Localidade;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NoSearchResultException;

import java.util.List;

public class BuscaDestinoPorNomeUseCase extends UseCase<BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeResult, BuscaDestinoPorNomeUseCase.BuscaDestinoPorNomeParams> {

    private final BuscaDestinoUseCase buscaDestinoUseCase;

    public BuscaDestinoPorNomeUseCase(final BuscaDestinoUseCase buscaDestinoUseCase){
        this.buscaDestinoUseCase = buscaDestinoUseCase;
    }

    @Override
    public BuscaDestinoPorNomeResult execute(final BuscaDestinoPorNomeParams params) {

        final BuscaDestinoUseCase.BuscaDestinoResult result =
                buscaDestinoUseCase.execute(new BuscaDestinoUseCase.BuscaDestinoParams(params.origem));

        if(result.isSuccess()){
            final Localidade destino = find(
                    result.result.listaLocalidade.lsLocalidade,
                    params.nome
            );
            if(null != destino){
                return new BuscaDestinoPorNomeResult(destino);
            }else{
                return new BuscaDestinoPorNomeResult(new NoSearchResultException());
            }
        }else{
            return new BuscaDestinoPorNomeResult(result.exception);
        }
    }

    private Localidade find(final List<Localidade> localidades, final String name){
        for(final Localidade localidade: localidades){
            System.out.println(localidade.cidade);
            if(localidade.cidade.toLowerCase().contains(name.toLowerCase())){
                return localidade;
            }
        }
        return null;
    }

    public static class BuscaDestinoPorNomeParams extends Params{
        public final Localidade origem;
        public final String nome;
        public BuscaDestinoPorNomeParams(final Localidade origem, final String nome){
            this.origem = origem;
            this.nome = nome;
        }
    }

    public static class BuscaDestinoPorNomeResult extends Result<Localidade>{
        public BuscaDestinoPorNomeResult(Localidade result) { super(result);        }
        public BuscaDestinoPorNomeResult(Exception exception) { super(exception); }
    }
}
