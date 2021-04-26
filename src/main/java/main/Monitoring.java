package main;

import arca.ci.OperadoraIntegration;
import arca.controllers.email.SendMail;
import arca.domain.entities.Operadora;
import arca.domain.usecases.None;
import arca.domain.usecases.Result;
import arca.domain.usecases.implementation.ValidarOperadoraUseCase;
import arca.logger.Logger;
import arca.util.DateUtils;
import arca.util.ThreadUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Monitoring {

    public static void main(String[] args) {
        final List<Operadora> operadoras = OperadoraIntegration.operadoras();
        operadoras.add(OperadoraIntegration.garcia());
        final HashMap<Operadora, Thread> map = new HashMap<>();
        for(final Operadora o : operadoras){
            final Thread thread = new Thread(new RunValidate(o));
            map.put(o, thread);
            System.out.println("Iniciando testes em "+o.nome);
            thread.start();
            ThreadUtils.sleepTreeSecond();
        }

        boolean alive = true;
        int pt = 1;
        while(alive){
            System.out.println("\nIteração "+pt+" em "+ DateUtils.currentDate());
            pt++;
            for(final Map.Entry<Operadora, Thread> entry : map.entrySet()){
                final Thread t = entry.getValue();
                final  Operadora o = entry.getKey();
                System.out.println(" Operadora: "+o.nome+" is "+t.isAlive());
            }
            System.out.println("\n");
            ThreadUtils.sleep(TimeUnit.MINUTES.toMillis(5));
        }
    }

    static final class RunValidate implements Runnable {
        final Operadora operadora;
        private RunValidate(final Operadora operadora){
            this.operadora = operadora;
        }
        @Override
        public void run() {
            while(true){
                final ValidarOperadoraUseCase useCase = new ValidarOperadoraUseCase(operadora);
                final MonitoringResult result = useCase.execute(new None());
                sendLogger(result);
                System.out.println(String.format("FALHA: %s", operadora.nome));
                ThreadUtils.sleepTreeMinutes();
            }
        }

        private void sendLogger(final MonitoringResult  result) {
            final String description = String.format(" ❌ %s ❌ %s", DateUtils.currentDate(), operadora.nome);
            final StringBuilder builder = new StringBuilder("FALHA DETECTADA!\n");
            builder.append(String.format("\uD83D\uDCA5 ERRO: %s\n", result.exception.toString()));
            builder.append(String.format("Logs: \n %s \n", result.result.toString()));
            SendMail.send(description, builder.toString(), "thiago.domacoski@arcasoltec.com.br");
        }
    }

    public static class MonitoringResult extends Result<Logger>{
        public MonitoringResult(final Logger result, final Exception e) {
            super(result);
            this.exception = e;
        }
    }
}
