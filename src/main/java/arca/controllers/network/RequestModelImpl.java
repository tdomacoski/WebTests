package arca.controllers.network;

import arca.domain.entities.ConexaoOperadora;
import arca.exceptions.NetworkException;
import arca.util.GsonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestModelImpl implements RequestModel {

    @Override
    public ResponseModel execute(final ConexaoOperadora conexaoOperadora,
                                 final String metodo, final RequestType type)
            throws NetworkException {
        try {
            final String ulrString = String.format("%s%s", conexaoOperadora.url, metodo);
            final URL url = new URL(ulrString);
            final HttpURLConnection connection = HttpURLConnection.class.cast(url.openConnection());
            connection.setRequestMethod(type.toString());
            if (null != conexaoOperadora.auth) {
                connection.setRequestProperty("Authorization", conexaoOperadora.auth);
            }
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
            if (isSucess) {
                return new ResponseModel(buffer.toString());
            } else {
                return new ResponseModel(GsonUtil.erro(buffer.toString()));
            }
        } catch (final IOException e) {
            throw new NetworkException(e, 0, e.getMessage());
        }
    }
}