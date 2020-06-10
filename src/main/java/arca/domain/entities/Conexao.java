package arca.domain.entities;

public class Conexao {
    public String url;
    public String auth;
    public Conexao(final String url, final String auth){
        this.url = url;
        this.auth = auth;
    }
}
