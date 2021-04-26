package arca.domain.entities;

public class Operadora {

    public final String nome;
    public final ConexaoOperadora vendas;
    public final ConexaoOperadora integra;
    public final String grupo;
    public final Long id;

    public Operadora(final String nome,
                     final String authVendas,
                     final String authIntegra,
                     final String urlVendas,
                     final String urlIintegra,
                     final String grupo,
                     final Long id) {
        this.nome = nome;
        this.vendas = new ConexaoOperadora(urlVendas, authVendas);
        this.integra = new ConexaoOperadora(urlIintegra, authIntegra);
        this.grupo =  grupo;
        this.id = id;
    }
}
