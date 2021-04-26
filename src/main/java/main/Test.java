package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.Localidade;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception{

        final List<Long> ids = new ArrayList<>(0);
        try (BufferedReader br = Files.newBufferedReader(Paths.get("garcia_rotas.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final Long id = Long.valueOf(line.trim());
                if(!ids.contains(id)){
                    ids.add(id);
                }
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
        final Logger logger = new LoggerFile("GARCIA");
        final List<Localidade> localidades =
                ApiIntegration.getOrigens(OperadoraIntegration.garcia(), logger);

        for(final Long id: ids){
            Localidade localidade = null;
            for(final Localidade l : localidades){
                if(l.id.equals(id)){
                    localidade = l;
                    break;
                }
            }
            if(null == localidade){
                System.out.println(id+" NÃO ENCONTRADO!!!!!!!");
            }else{
                System.out.println(localidade.toJson());
            }
        }
    }
}
