package arca.exceptions;

public class ParseException extends Exception {
    public ParseException(final String json){
        super(String.format("no parse json: %s", json));
    }
}
