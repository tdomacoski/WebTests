package arca.domain.usecases.implementation;

import arca.domain.entities.Localidade;
import arca.domain.usecases.None;
import arca.domain.usecases.Params;
import arca.domain.usecases.Result;
import arca.domain.usecases.UseCase;
import arca.exceptions.NoSearchResultException;

import java.util.List;

public class BuscaOrigemPorNomeUseCase  extends UseCase<BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeResult, BuscaOrigemPorNomeUseCase.BuscaOrigemPorNomeParams> {


    private final BuscaOrigemUseCase buscaOrigemUseCase;

    public BuscaOrigemPorNomeUseCase(final BuscaOrigemUseCase buscaOrigemUseCase){
        this.buscaOrigemUseCase = buscaOrigemUseCase;
    }


    @Override
    public BuscaOrigemPorNomeResult execute(BuscaOrigemPorNomeParams params) {

           final BuscaOrigemUseCase.BuscaOrigemResult result  =
                   buscaOrigemUseCase.execute(new None());
           if(result.isSuccess()){
               final Localidade localidade = find(
                       result.result.listaLocalidade.lsLocalidade,
                       params.nome
               );
               if(null != localidade){
                   return new BuscaOrigemPorNomeResult(localidade);
               }else{
                   return new BuscaOrigemPorNomeResult(new NoSearchResultException());
               }
           }else{
               return new BuscaOrigemPorNomeResult(result.exception);
           }
    }

    private Localidade find(final List<Localidade> localidades, final String name){
        for(final Localidade localidade: localidades){
            System.out.println(localidade.cidade+" "+localidade.id);
            if(localidade.cidade.toLowerCase().contains(name.toLowerCase())){
                return localidade;
            }
        }
        return null;
    }

    public static class BuscaOrigemPorNomeParams extends Params{
        public final String nome;
        public BuscaOrigemPorNomeParams(final String nome){
            this.nome = nome;
        }
    }

    public static class BuscaOrigemPorNomeResult extends Result<Localidade>{
        public BuscaOrigemPorNomeResult(Localidade result) { super(result); }
        public BuscaOrigemPorNomeResult(Exception exception) { super(exception); }
    }
}
