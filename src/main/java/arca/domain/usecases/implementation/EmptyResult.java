package arca.domain.usecases.implementation;

import arca.domain.usecases.None;
import arca.domain.usecases.Result;

public class EmptyResult extends Result<None> {
    public EmptyResult(None result) {
        super(result);
    }

    public EmptyResult(Exception exception) {
        super(exception);
    }
}
