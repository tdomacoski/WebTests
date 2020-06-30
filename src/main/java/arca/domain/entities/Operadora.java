package arca.domain.entities;

public class Operadora {

    public final String nome;
    public final ConexaoOperadora vendas;
    public final ConexaoOperadora integra;


    public Operadora(final String nome,
                     final String authVendas,
                     final String authIntegra,
                     final String urlVendas,
                     final String urlIintegra) {
        this.nome = nome;
        this.vendas = new ConexaoOperadora(urlVendas, authVendas);
        this.integra = new ConexaoOperadora(urlIintegra, authIntegra);
    }
}