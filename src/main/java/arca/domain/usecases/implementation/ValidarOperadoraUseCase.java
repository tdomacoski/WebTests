package arca.domain.usecases.implementation;

import arca.ci.RequestModelIntegration;
import arca.controllers.network.RequestModel;
import arca.domain.entities.Operadora;
import arca.domain.usecases.None;
import arca.domain.usecases.UseCase;
import arca.logger.Logger;
import arca.logger.LoggerBuffer;
import main.Monitoring;

public class ValidarOperadoraUseCase extends UseCase<Monitoring.MonitoringResult, None> {

    private final Operadora operadora;
    private final Logger emptyLogger = new EmptyLogger();
    private final Logger emailLogger = new LoggerBuffer();


    private final RequestModel requestModel = RequestModelIntegration.getRequestModel();

    private final long[] medias = {0,0,0,0,0,0,0,0,0,0};


    public ValidarOperadoraUseCase(final Operadora operadora) {
        this.operadora = operadora;
    }

    @Override
    public Monitoring.MonitoringResult execute(None params) {
        try{
            while(true){

            }
        }catch (final Exception e){
            return new Monitoring.MonitoringResult(emailLogger, e);
        }

    }

    class EmptyLogger implements Logger {
        @Override
        public void add(String log) {
        }

        @Override
        public void error(Exception error) {
        }
    }
}