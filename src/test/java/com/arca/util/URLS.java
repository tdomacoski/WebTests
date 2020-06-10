package com.arca.util;

public class URLS {
    public static final String URL_HOMOLOG = "https://embarcai-sales-staging.herokuapp.com";
    public static final String URL_PROD = "http://embarcai-sales.herokuapp.com";

    public static String getLogin(boolean homolog){
        return String.format("%s/sessions/new", (homolog?URL_HOMOLOG:URL_PROD) );
    }

    public static String getCadastro(boolean homolog){
        return String.format("%s/customers/new", (homolog?URL_HOMOLOG:URL_PROD) );
    }
}