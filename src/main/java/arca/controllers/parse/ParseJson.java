package arca.controllers.parse;

import arca.exceptions.ParseException;

public interface ParseJson<T> {
     final String EMPTY = "";
     T parse(final String json) throws ParseException;
     String transform(final T t) throws ParseException;
}
