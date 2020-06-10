package arca.controllers.parse;

public interface ParseJson<T> {
     T parse(final String json);
     String transform(final T t);
}
