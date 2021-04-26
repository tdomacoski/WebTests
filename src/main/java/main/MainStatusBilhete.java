package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.database.pojo.StatusBilhete;
import arca.database.query.status.QueryStatusBilhete;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.Operadora;
import arca.logger.Logger;

import java.util.List;
import java.util.Scanner;

public class MainStatusBilhete {
    private final Logger loggerEmpty = new Logger() {
        @Override
        public void error(Exception error) {}
        @Override
        public void add(String log) {}
    };
    private final QueryStatusBilhete query = new QueryStatusBilhete();


    public static void main(String[] args) {
        try{

            Long orderId = null;
            while(true) {
                Scanner in = new Scanner(System.in);
                System.out.print("Informe o número da Order: ");
                String s = in.nextLine();
                try{

                    orderId = Long.valueOf(s);
                    break;
                }catch (final Exception e){
                    System.out.println("Número inválido!");
                }
            }

            final MainStatusBilhete main = new MainStatusBilhete();
            main.execute(orderId);
        }catch (final Exception e){
            e.printStackTrace();
        }
    }

    private final void execute(final Long orderId)throws Exception{
        System.out.println(String.format("Pesquisando dados da OrderId #%s", orderId.toString()));
        final List<StatusBilhete> list = query.execute(orderId);
        System.out.println("Encontrado "+list.size()+" bilhetes");
        for(final StatusBilhete bilhete: list){
            System.out.println(String.format("Nome: %s, RG: %s [%s]", bilhete.nome, bilhete.rg, bilhete.poltrona));
            final BuscaStatusBilhete buscaStatusBilhete = consulta(bilhete);
            System.out.println(buscaStatusBilhete.toJson());
        }
    }


    private BuscaStatusBilhete consulta(final StatusBilhete b) throws Exception {
        final Operadora operadora = OperadoraIntegration.byId(b.operadora);
        return ApiIntegration.statuBilhete(operadora, loggerEmpty, operadora.grupo, b.servico, b.numBilhete, b.origem, b.destino, b.data, b.poltrona);
    }
}
