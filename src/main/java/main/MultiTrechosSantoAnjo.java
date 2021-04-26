package main;

import arca.ci.OperadoraIntegration;
import arca.domain.entities.Operadora;
import arca.domain.usecases.implementation.BuscaMultiTrechoUseCase;
import arca.logger.LoggerFile;

import java.util.ArrayList;
import java.util.List;

public class MultiTrechosSantoAnjo {

    public static final List<BuscaMultiTrechoUseCase.MultiRota> load(){
        final List<BuscaMultiTrechoUseCase.MultiRota> list = new ArrayList<>(0);
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L,17691L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L,17645L, 17692L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L, 17693L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L,17686L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17693L, 17645L,15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17686L,17645L,15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L, 22027L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L,17650L));
        list.add(new BuscaMultiTrechoUseCase.MultiRota(18253L, 17645L,15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17647L, 17645L,15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L,17647L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(15921L, 17645L,18253L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(22027L, 17645L, 15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17650L, 17645L, 15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17691L, 17645L, 15921L));
        list.add(new BuscaMultiTrechoUseCase.
                MultiRota(17692L, 17645L,15921L));

        return list;
    }
    public static void main(String[] args) {
        try {
            final LoggerFile loggerFile = new LoggerFile("santo_anjo_multitrecho");
            final Operadora operadora = OperadoraIntegration.santoAnjo2();
            final List<BuscaMultiTrechoUseCase.MultiRota> rotas = load();
            final BuscaMultiTrechoUseCase useCase = new BuscaMultiTrechoUseCase(operadora, loggerFile);
            for(final BuscaMultiTrechoUseCase.MultiRota rota: rotas){
                useCase.execute(new BuscaMultiTrechoUseCase.MultiTrechoParams(rota));
            }
        }catch (final Exception e){
            e.printStackTrace();
        }


    }

}
