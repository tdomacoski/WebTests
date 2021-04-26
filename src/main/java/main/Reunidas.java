package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.ConsultaServicos;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.domain.entities.Servico;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reunidas {

    public static void main(String[] args) throws Exception {

        final Operadora operadora = OperadoraIntegration.reunidas();
        final Logger logger = new LoggerFile("REUNIDAS");

        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
        final HashMap<String, List<Servico>> map = new HashMap<>();
        final List<Localidade> origens = ApiIntegration.getOrigens(operadora, logger);
        for (final Localidade origem : origens) {

            final List<Localidade> destinos = ApiIntegration.getDestinos(operadora, logger, origem);
            for (final Localidade destino : destinos) {
                final ConsultaServicos servicos = ApiIntegration.getBuscaViagem(operadora, logger, origem, destino, calendar);

                for(final Servico servico: servicos.lsServicos){
                    if(map.containsKey(servico.empresa)){
                        map.get(servico.empresa).add(servico);
                        System.out.println("+ "+servico.empresa);
                        System.out.println(String.format("De: %s (%s) para : %s (%s)", origem.cidade, origem.uf, destino.cidade, destino.uf));
                        System.out.println(servico.toJson());
                    }else{
                        System.out.println("Add "+servico.empresa);
                        final List<Servico> s = new ArrayList<>(0);
                        s.add(servico);
                        System.out.println(String.format("De: %s (%s) para : %s (%s)", origem.cidade, origem.uf, destino.cidade, destino.uf));
                        System.out.println(servico.toJson());
                        map.put(servico.empresa, s);
                    }
                }

            }
        }


    }
}
