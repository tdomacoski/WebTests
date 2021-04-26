package main;

import arca.ci.ApiIntegration;
import arca.ci.UseCaseIntegration;
import arca.domain.entities.*;
import arca.domain.usecases.implementation.BuscaOnibusUseCase;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.ThreadUtils;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Assentos {


    private Operadora operadora;
    private Logger logger;
    private Logger uLogger;
    private Calendar data;

    public Assentos(final Operadora operadora){
        this.operadora = operadora;
        this.logger = new LoggerFile(String.format("assentos_%s", operadora.nome));
        this.uLogger = new LoggerFile(operadora.nome);
        this.data = Calendar.getInstance();
        this.data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(5));
    }


    private final void run() throws Exception{
        final List<Localidade> origens = ApiIntegration.getOrigens(operadora, uLogger);
        for(final Localidade origem : origens){
            final ConsultaServicos servicos = findServicos(origem);
            if(null != servicos){
                if(servicos.lsServicos.isEmpty()){
                    continue;
                }
                for(final Servico servico: servicos.lsServicos){
                    int poltronasLivres = servico.poltronasLivres;
                    final Onibus onibus = findOnibus(origem, servicos.destino, servico);
                    ThreadUtils.sleepTreeSecond();

                    int assentosLivres = 0;
                    for (final Poltrona poltrona : onibus.mapaPoltrona) {
                        if (poltrona.disponivel) {
                            assentosLivres++;
                        }
                    }
                    if(assentosLivres != poltronasLivres){
                        logger.add(String.format("De %s  para %s [Servico: %s] assentosLivres: %d poltronasDisponiveis: %d",
                                origem.cidade, servicos.destino.cidade, servico.servico,
                                assentosLivres, poltronasLivres));
                    }
                }
            }
        }
    }

    private Onibus findOnibus(final Localidade origem, final Localidade destino,
                              final Servico servico)throws Exception{
       return
        ApiIntegration.getOnibus(operadora, uLogger, origem, destino,data, servico).
                onibus;
    }
    private final ConsultaServicos findServicos(final Localidade origem) throws Exception{
        final List<Localidade> destinos = ApiIntegration.getDestinos(
                operadora, uLogger,origem);
        for(final Localidade destino : destinos){
            final ConsultaServicos servicos = ApiIntegration.
                    getBuscaViagem(operadora, uLogger, origem, destino, data);
            ThreadUtils.sleepOneSecond();
            if(servicos.lsServicos.isEmpty()){
                continue;
            }else{
                return servicos;
            }
        }
        return null;
    }

}
