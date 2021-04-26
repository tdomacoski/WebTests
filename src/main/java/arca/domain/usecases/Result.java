package arca.domain.usecases;

import arca.domain.entities.Error;

public class Result<R>{

    public  R result;
    public  Exception exception;
    public Error error;

    public Result(final R result){
        this.result = result;
        this.exception = null;
        this.error = null;
    }
    public Result(final Exception exception){
        this.exception = exception;
        this.result = null;
        this.error = null;
    }
    public Result(final Error error){
        this.error = error;
        this.result = null;
        this.exception = null;
    }

    public boolean isSuccess(){
        return exception == null && error == null;
    }

}
