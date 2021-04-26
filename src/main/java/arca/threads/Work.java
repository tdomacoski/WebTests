package arca.threads;

import arca.ci.ApiIntegration;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Work  {
    public boolean isWorking = true;

    private final Operadora operadora;
    private final Logger logger;

    public Work(  final Operadora operadora, final Logger logger) {
        this.operadora = operadora;
        this.logger = logger;
    }
    public void execute(){
        new Thread(new Execute()).start();
    }


    private boolean checkLocalidades() {
        try {
            long ini = System.currentTimeMillis();
            final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
            long time = System.currentTimeMillis() - ini;
            System.out.println(String.format("%S segundos com %s localidades",
                    TimeUnit.MILLISECONDS.toSeconds(time) + " ", localidades.size() + " "));

            return true;
        } catch (final Exception e) {
            return false;
        }
    }


    class Execute implements Runnable {
        @Override
        public void run() {
            int p = 0;
            while (isWorking) {
                checkLocalidades();
                if (p != 18) {
                    isWorking = false;
                }
                p++;
            }

        }
    }


}
