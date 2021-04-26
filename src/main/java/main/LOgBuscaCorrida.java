package main;

import arca.controllers.network.RequestModelImpl;
import arca.exceptions.NetworkException;
import arca.logger.Logger;
import arca.logger.LoggerFile;
import arca.util.DateUtils;
import arca.util.ThreadUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

public class LOgBuscaCorrida {

    private static final String URL = "http://189.2.223.233:8282/VendaWebService/rj/buscaOnibus?origem=24548&destino=24549&data=2021-02-19&servico=8888&grupo=PRINC";

    public static void main(String[] args) {

        final Logger logger = new LoggerFile("EMBARCA");
        logger.add(String.format("MONITORANDO A URL : %S", URL));


        final long finalMOnitoring = System.currentTimeMillis()+ TimeUnit.HOURS.toMillis(1);

       logger.add(String.format("Vai monitorar até %s ", DateUtils.full.format(finalMOnitoring)));

       while(finalMOnitoring > System.currentTimeMillis()){

           try{
               final String result = execute(URL);
                System.out.println(result);
                logger.add(result);
               ThreadUtils.sleep(TimeUnit.MINUTES.toMillis(5));

           }catch (final Exception e){
               e.printStackTrace();
           }
       }





    }


    public static String execute(final String ulrString)throws NetworkException {
        try {
            final URL url = new URL(ulrString);
            final HttpURLConnection connection = HttpURLConnection.class.cast(url.openConnection());
            connection.setRequestMethod("GET");
                connection.setRequestProperty("Authorization", "Basic cmpBdXRoV3M6MU1Zbk90Ulg0Zw==");
            int responseCode = connection.getResponseCode();
            BufferedReader reader = null;
            boolean isSucess = HttpURLConnection.HTTP_OK == responseCode;
            if (isSucess) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }
            String line;
            final StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            connection.disconnect();
            reader.close();
           return  buffer.toString();
        } catch (final IOException e) {
            throw new NetworkException(e, 0, e.getMessage());
        }
    }
}
