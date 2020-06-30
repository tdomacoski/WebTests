package arca.domain.entities;

public class ConexaoOperadora {
    public String url;
    public String auth;
    public ConexaoOperadora(final String url, final String auth){
        this.url = url;
        this.auth = auth;
    }
}
