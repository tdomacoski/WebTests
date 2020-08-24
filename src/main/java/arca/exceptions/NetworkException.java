package arca.exceptions;

import java.io.IOException;

public class NetworkException extends IOException {
    public final Integer statusCode;
    public final String message;
    public NetworkException(final Throwable t, int statusCode, final String message){
        super(t);
        this.statusCode = statusCode;
        this.message = message;
    }
}
