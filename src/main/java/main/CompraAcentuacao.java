package main;

import arca.ci.OperadoraIntegration;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.net.URLEncoder;

public class CompraAcentuacao {


    private static String passageiro = "João Lúcio Magalhães";
    private static String documento = "MG13956654";
    private static final Logger LOGGER = new LoggerFile(CompraAcentuacao.class.getSimpleName());

    public static void main(String[] args) {
        for (final Operadora operadora : OperadoraIntegration.operadoras()) {
            try {
                new Compras(operadora, LOGGER, URLEncoder.encode(passageiro, "utf-8"), documento).exec();
            } catch (final Exception e) {
                e.printStackTrace();
                LOGGER.add(e.getMessage());
            }
        }
    }
}
