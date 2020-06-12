package arca.controllers.parse;

public interface ParseJson<T> {
     final String EMPTY = "";
     T parse(final String json);
     String transform(final T t);
}
