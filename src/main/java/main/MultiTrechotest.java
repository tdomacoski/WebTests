package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.*;
import arca.exceptions.NoSearchResultException;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.util.Calendar;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class MultiTrecho {

    public static void main(String[] args) {
        try{
            new MultiTrecho(OperadoraIntegration.garcia()).run();
        }catch (final Exception e){
            e.printStackTrace();
        }

    }
    private final String cidadeOrigem = "LONDRINA";
    private final String cidadeDestino = "CANOAS";
    private final Operadora operadora;
    private final Logger logger = new LoggerFile("chamado");
    private final Calendar data;

    public MultiTrecho(final Operadora operadora){
        this.operadora = operadora;
        this.data = Calendar.getInstance();
        this.data.setTimeInMillis(System.currentTimeMillis()+ TimeUnit.DAYS.toMillis(3));
    }


    private final void run() throws Exception{
         final Localidade origem =  new Localidade();
         origem.id = 12984l;
         origem.cidade = "LONDRINA - PR";
         origem.uf = "PR";

//                 ApiIntegration.
//                 getOrigemByName(operadora, logger, cidadeOrigem);
         final Localidade destino = ApiIntegration.
                 getDestinoByName(operadora, logger, origem, cidadeDestino);

         final ConsultaServicos servicos = ApiIntegration.
                 getBuscaViagem(operadora, logger, origem, destino, data);

         if(servicos.lsServicos.isEmpty()){
             throw new NoSearchResultException();
         }
         final Servico servico = servicos.lsServicos.get(0);
         if(servico.conexao == null){
             throw new Exception("Sem conexão!");
         }


         final ConsultaOnibus onibus1 = ApiIntegration.getOnibus(operadora, logger, origem,
                 destino, data, servico);

         final ConsultaOnibus onibus2 = ApiIntegration.getOnibusConexao(operadora, logger,
                 origem, destino, data, servico.conexao.servicoConexao, servico.grupo);










    }





}
