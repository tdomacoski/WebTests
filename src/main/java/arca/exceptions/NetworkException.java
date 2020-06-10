package arca.exceptions;

public class NetworkException extends Exception {
    public final Integer statusCode;
    public final String message;
    public NetworkException(final Throwable t, int statusCode, final String message){
        super(t);
        this.statusCode = statusCode;
        this.message = message;
    }
}
