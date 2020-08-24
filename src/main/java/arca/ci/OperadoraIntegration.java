package arca.ci;

import arca.domain.entities.Operadora;


public class OperadoraIntegration {
    public static Operadora ouroEPrata() {
        return new Operadora(
                "Ouro e Prata",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic Z2FyY2lhOnZnYXJjaWE3NjU0",
                "http://18.235.188.113:8680/VendaWebService/rj/",
                ""
        );
    }
    public static Operadora garcia() {
        return new Operadora(
                "Garcia",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic Z2FyY2lhOnZnYXJjaWE3NjU0",
                "http://200.155.58.11:8980/VendaWebService/rj/",
                "http://200.155.58.11:8282/RJIntegraGarcia/"
        );
    }
    public static Operadora graciosa() {
        return new Operadora(
                "Graciosa",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic Z3JhY2lvc2EyMDE5OmdyYWNpb3NhMjAxOQ==",
                "http://177.101.148.227:8180/VendaWebService/rj/",
                "http://177.101.148.227:8081/RJIntegra/"
        );
    }
    public static Operadora princesaDosCampos() {
        return new Operadora(
                "Princesa dos Campos",
                "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
                "Basic cHJpbmNlc2E6cHJpbmNlc2ExMjM=",
                "http://189.2.223.233:8282/VendaWebService/rj/",
                "http://189.2.223.233:8089/RJIntegra/"
        );
    }
    public  static Operadora santAnjo(){
        return new Operadora(
          "Santo Anjo",
          "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
          "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==",
          "http://177.101.148.227:8080/VendaWebService/rj/",
                ""
        );
    }
}
