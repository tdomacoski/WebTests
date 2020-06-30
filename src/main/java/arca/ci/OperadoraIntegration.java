package arca.ci;

import arca.domain.entities.Operadora;


public class OperadoraIntegration {

    public static Operadora garcia() {
        return new Operadora(
                "Garcia",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic Z2FyY2lhOnZnYXJjaWE3NjU0",
                "http://200.155.58.11:8980/VendaWebService/rj/",
                "http://200.155.58.11:8282/RJIntegraGarcia/"
        );
    }

    public static Operadora princesaDosCampos() {
        return new Operadora(
                "Princesa dos Campos",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic cHJpbmNlc2E6cHJpbmNlc2ExMjM=",
                "http://189.2.223.233:8380/VendaWebService/rj/",
                "http://189.2.223.233:8089/RJIntegra/"
        );
    }
}
