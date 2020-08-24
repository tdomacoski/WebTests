package arca.controllers.network;

import arca.domain.entities.ConexaoOperadora;
import arca.exceptions.NetworkException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestModelImpl implements RequestModel {

    @Override
    public String execute(ConexaoOperadora conexaoOperadora, String metodo, String tipo) throws NetworkException {
        try {
            final String ulrString = String.format("%s%s", conexaoOperadora.url, metodo);
            final URL url = new URL(ulrString);
            final HttpURLConnection connection = HttpURLConnection.class.cast(url.openConnection());
            connection.setRequestMethod(tipo);
            if (null != conexaoOperadora.auth) {
                connection.setRequestProperty("Authorization", conexaoOperadora.auth);
            }
            int responseCode = connection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == responseCode) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                connection.disconnect();
                reader.close();
                return response.toString();
            } else {
                throw new NetworkException(null, responseCode, String.format("status code: %d", responseCode));
            }
        } catch (final IOException e) {
            throw new NetworkException(e, 0, e.getMessage());
        }
    }
}