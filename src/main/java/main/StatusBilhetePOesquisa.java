package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.database.postgre.AWSPostgres;
import arca.database.query.status.QueryStatusBilhete;
import arca.domain.entities.BuscaStatusBilhete;
import arca.domain.entities.DevolvePoltrona;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.GsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class StatusBilhetePOesquisa {
//    SANJO

    public static Operadora operadora = OperadoraIntegration.santAnjo();
    public static final Logger LOGGER = new LoggerFile("AAAA" + operadora.nome);


    public static void main(String[] args)throws Exception {


        new QueryStatusBilhete().run();

//        for(final Bilhete bilhete : BILHETES()){
//            final BuscaStatusBilhete buscaStatusBilhete = consulta(bilhete);
//            LOGGER.add(buscaStatusBilhete.toJson());
//            System.out.println(buscaStatusBilhete.toJson());
//        }

//        final Lista lista  = cancelamento();
//        for(final Cancelamento c: lista.bilhetes){
//            LOGGER.add(String.format(" - - - > CANCELAMENTO : %s", GsonUtil.GSON.toJson(c)));
//            final DevolvePoltrona sp = cancelamento(c);
//            LOGGER.add(sp.toJson());
//
//        }

    }

    public static BuscaStatusBilhete consulta(final Bilhete b) throws Exception {
        return ApiIntegration.statuBilhete(operadora, LOGGER, b.grupo, b.servico, b.numBilhete, b.origem, b.destino, b.data, b.poltrona);
    }

    public static DevolvePoltrona cancelamento(final Cancelamento c ) throws Exception {
        return ApiIntegration.cancelarPoltrona(operadora, LOGGER,
                c.origem, c.destino, c.data, c.servico, "SANJO",
                c.id_transacao, c.bilhete, c.poltrona);
    }



    public static Lista cancelamento () throws Exception{
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = Files.newBufferedReader(Paths.get("Sanjo.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        return GsonUtil.GSON.fromJson(sb.toString(), Lista.class);
    }

    private static  Bilhete[] BILHETES() {
        final Bilhete[] bilhetes = {null, null, null, null, null, null};
        bilhetes[0] = new Bilhete("SANJO", "27047",
                "10000000927855", "17412", "15921", "2020-12-02", "3");
        bilhetes[1] = new Bilhete("SANJO", "27047",
                "10000000927857", "17412", "15921", "2020-12-02", "6");
        bilhetes[2] = new Bilhete("SANJO", "27047",
                "10000000927856", "17412", "15921", "2020-12-02", "4");

        bilhetes[3] = new Bilhete("SANJO", "48047",
                "10000000927844", "15921", "17412", "2020-11-25", "2");
        bilhetes[4] = new Bilhete("SANJO", "48047",
                "10000000927845", "15921", "17412", "2020-11-25", "4");
        bilhetes[5] = new Bilhete("SANJO", "48047",
                "10000000927847", "15921", "17412", "2020-11-25", "3");

        return bilhetes;
    }

    static class Cancelamento{
        public String origem, poltrona, servico,bilhete, nome_operadora, destino, id_transacao, data;
        public Integer operadora;
        public Long order_id;
    }

    static class Lista{
        List<Cancelamento> bilhetes;
    }

    static class Bilhete {
        public final String grupo;
        public final String servico;
        public final String numBilhete;
        public final String origem;
        public final String destino;
        public final String data;
        public final String poltrona;

        public Bilhete(final String grupo,
                       final String servico,
                       final String numBilhete,
                       final String origem,
                       final String destino,
                       final String data,
                       final String poltrona) {
            this.grupo = grupo;
            this.servico = servico;
            this.numBilhete = numBilhete;
            this.origem = origem;
            this.destino = destino;
            this.data = data;
            this.poltrona = poltrona;
        }

    }
}
