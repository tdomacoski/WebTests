package arca.exceptions;

public class NoSearchResultException extends Exception{

    public NoSearchResultException(){
        super();
    }
    public NoSearchResultException(final String message){
        super(message);
    }
}
