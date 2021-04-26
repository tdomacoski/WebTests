package arca.domain.entities;

public class Conexao {
    public String servicoConexao;
    public String dia;
    public String via;
    public String primeiroTrechoClasse;
    public String primeiroTrechoDataSaida;
    public String primeiroTrechoDataChegada;
    public String primeiroTrechoHoraSaida;
    public String primeiroTrechoHoraChegada;
    public String primeiroTrechoPreco;
    public String segundoTrechoClasse;
    public String segundoTrechoDataSaida;
    public String segundoTrechoDataChegada;
    public String segundoTrechoHoraSaida;
    public String segundoTrechoHoraChegada;
    public String segundoTrechoPreco;
    public String diaConexao;
    public String diaChegadaCompleta;

    @Override
    public String toString() {
        return "Conexao{" +
                "servicoConexao='" + servicoConexao + '\'' +
                ", dia='" + dia + '\'' +
                ", via='" + via + '\'' +
                ", primeiroTrechoClasse='" + primeiroTrechoClasse + '\'' +
                ", primeiroTrechoDataSaida='" + primeiroTrechoDataSaida + '\'' +
                ", primeiroTrechoDataChegada='" + primeiroTrechoDataChegada + '\'' +
                ", primeiroTrechoHoraSaida='" + primeiroTrechoHoraSaida + '\'' +
                ", primeiroTrechoHoraChegada='" + primeiroTrechoHoraChegada + '\'' +
                ", primeiroTrechoPreco='" + primeiroTrechoPreco + '\'' +
                ", segundoTrechoClasse='" + segundoTrechoClasse + '\'' +
                ", segundoTrechoDataSaida='" + segundoTrechoDataSaida + '\'' +
                ", segundoTrechoDataChegada='" + segundoTrechoDataChegada + '\'' +
                ", segundoTrechoHoraSaida='" + segundoTrechoHoraSaida + '\'' +
                ", segundoTrechoHoraChegada='" + segundoTrechoHoraChegada + '\'' +
                ", segundoTrechoPreco='" + segundoTrechoPreco + '\'' +
                ", diaConexao='" + diaConexao + '\'' +
                ", diaChegadaCompleta='" + diaChegadaCompleta + '\'' +
                '}';
    }
}
