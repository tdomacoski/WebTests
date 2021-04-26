package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.*;
import arca.logger.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class TestePerformance {



    public static int ptOrigem, ptDestino, ptServico  = 0;
    public static long[] origensTimes = {0,0,0,0,0,0,0,0,0,0};
    public static long[] destinosTimes = {0,0,0,0,0,0,0,0,0,0};
    public static long[] servicosTimes = {0,0,0,0,0,0,0,0,0,0};


    public static void setTime(final long t, long[] array, int pt){
        array[pt%array.length] = t;
        pt++;
    }


    public static void printMedia( long[] array){
        long total = 0l;
        for(final Long t : array){
            total += t;
        }
        long media = (total/array.length);
        System.out.println(String.format("Tempo: %s milis", media));
    }


    public static void setTimeOrigem(long t ){
        setTime(t, origensTimes, ptOrigem);
    }

    public static void setTimeDestino(long t ){
        setTime(t, destinosTimes, ptDestino);
    }

    public static void setTimeServico(long t ){
        setTime(t, servicosTimes, ptServico);
    }



    private final Logger logger;
    private final Operadora operadora;

    TestePerformance(final Operadora operadora) {
        this.operadora = operadora;
        this.logger = new Logger() {
            @Override
            public void add(String log) {
            }
            @Override
            public void error(Exception error) {
            }
        };

//        new LoggerFile(String.format("%s_%s", TestePerformance.class.getSimpleName(), operadora.nome));
    }


    public static void main(String[] args) {

        new TestePerformance(OperadoraIntegration.expressoPrincesaDosCampos()).execute();



    }




    private final void execute(){
        try{
            int pt = 0;
            while(pt != 55 ){
                getServico();
                printMedia(servicosTimes);
                pt++;
            }



        }catch (final Exception e){
            e.printStackTrace();
        }
    }



    private void getServico() throws Exception {
        Localidade origem = null;
        Localidade destino = null;
        Servico servico = null;
        Poltrona poltrona = null;


        final Calendar data = Calendar.getInstance();
        data.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));

        int pt = 0;
        while (pt != 33) {
            origem = getOrigem();
            destino = getDestino(origem);
            if (destino == null) {
                logger.add("Procurando destinos. . . ");
                continue;
            }
            long init = System.currentTimeMillis();
            servico = getServico(origem, destino, data);
            if (null == servico) {
                System.out.print("-");
                continue;
            }else{
                long time = System.currentTimeMillis() - init;
                setTimeServico(time);
                pt++;
                System.out.print("X");
            }

        }
    }

    private void checkOrigens () throws  Exception {
        int pt = 0;
        while (pt <= 33){
            pt++;
            final long ini = System.currentTimeMillis();
            ApiIntegration.getOrigens(operadora, logger);
            final long time = ( System.currentTimeMillis()- ini);
            setTimeOrigem(time);
        }
    }
    
    
    private final Localidade getOrigem() throws Exception {
        final List<Localidade> localidades = ApiIntegration.getOrigens(operadora, logger);
        int r = 0;
        final Random random = new Random();
        while (r <= 0) {
            r = random.nextInt();
        }
        return localidades.get((r % localidades.size()));
    }



    private final Servico getServico(final Localidade origem, final Localidade destino, final Calendar data) throws Exception {
        final ConsultaServicos servicos = ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, data);

        if (servicos.lsServicos.isEmpty()) {
            return null;
        } else {
            return servicos.lsServicos.get(0);
        }
    }

    private final Localidade getDestino(final Localidade origem) throws Exception {
        final List<Localidade> localidades = ApiIntegration.getDestinos(operadora, logger, origem);
        if (localidades.isEmpty()) {
            return null;
        }
        int r = 0;
        final Random random = new Random();
        while (r <= 0) {
            r = random.nextInt();
        }
        return localidades.get((r % localidades.size()));
    }





}




