package arca.domain.usecases;

public abstract class UseCase<R extends Result,P extends Params> {
    public abstract R execute(P params);
}