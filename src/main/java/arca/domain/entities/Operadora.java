package arca.domain.entities;

public class Operadora {

    public final String nome;
    public final Conexao vendas;
    public final Conexao integra;


    public Operadora(final String nome,
                     final String authVendas,
                     final String authIntegra,
                     final String urlVendas,
                     final String urlIintegra) {
        this.nome = nome;
        this.vendas = new Conexao(urlVendas, authVendas);
        this.integra = new Conexao(urlIintegra, authIntegra);
    }
}