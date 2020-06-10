package arca.domain.usecases;

public abstract class Result<R>{

    public R result;
    public Exception exception;

    public Result(final R result){
        this.result = result;
        this.exception = null;
    }
    public Result(final Exception exception){
        this.exception = exception;
        this.result = null;
    }
    public boolean isSuccess(){
        return result != null;
    }

}
