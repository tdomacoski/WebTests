package arca.domain.entities;

import com.google.gson.annotations.SerializedName;

public class Venda extends JsonModel {
    @SerializedName("Tarifa")
    public Float tarifa;
    @SerializedName("Doc passageiro")
    public String docPassageiro;
    @SerializedName("Chegada")
    public String chegada;
    @SerializedName("n° Bilhete")
    public String bilhete;
    @SerializedName("BPe pedagio")
    public Float bpePedagio;
    @SerializedName("Status Compra")
    public String statusCompra;
    @SerializedName("BPe outros")
    public Float bpeOutros;
    @SerializedName("Operadora")
    public String operadora;
    @SerializedName("Origem")
    public String origem;
    @SerializedName("BPe taxaEmbarque")
    public Float bpeTaxaEmbarque;
    @SerializedName("BPe tarifa")
    public Float bpeTarifa;
    @SerializedName("BPe valorFormaPagamento")
    public Float formaPagamento;
    @SerializedName("BPe desconto")
    public Float bpeDesconto;
    @SerializedName("Assento")
    public String assento;
    @SerializedName("Nome passageiro")
    public String passageiro;
    @SerializedName("Destino")
    public String destino;
    @SerializedName("BPe valorTotal")
    public Float bpeValorTotal;
    @SerializedName("Outros")
    public Float outros;
    @SerializedName("Data pagamento")
    public String dataPagamento;
    @SerializedName("Taxa Embarque")
    public Float taxaEmbarque;
    @SerializedName("Nome comprador")
    public String comprador;
    @SerializedName("ID Wirecard")
    public String wirecardId;
    @SerializedName("BPe valorPagar")
    public Float bpeValorPagar;
    @SerializedName("Saída")
    public String saida;
    @SerializedName("Pedágio")
    public Float pedagio;
    @SerializedName("Total Passagem")
    public Float totalPassagem;
    @SerializedName("BPe seguro")
    public Float bpeSeguro;
    @SerializedName("ID da Compra")
    public Long compraId;
    @SerializedName("BPe outrosTributos")
    public String bpeOutrosTributos;
    @SerializedName("Seguro")
    public Float seguro;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s,", isNotNull(compraId)));
        sb.append(String.format("%s,", isNotNull(dataPagamento)));
        sb.append(String.format("%s,", isNotNull(statusCompra)));
        sb.append(String.format("%s,", isNotNull(passageiro)));
        sb.append(String.format("%s,", isNotNull(comprador)));
        sb.append(String.format("%s,", isNotNull(docPassageiro)));
        sb.append(String.format("%s,", isNotNull(operadora)));
        sb.append(String.format("%s,", isNotNull(origem)));
        sb.append(String.format("%s,", isNotNull(saida)));
        sb.append(String.format("%s,", isNotNull(destino)));
        sb.append(String.format("%s,", isNotNull(chegada)));
        sb.append(String.format("%s,", isNotNull(bilhete)));
        sb.append(String.format("%s,", isNotNull(assento)));
        sb.append(String.format("%s,", isNotNull(totalPassagem)));
        sb.append(String.format("%s,", isNotNull(tarifa)));
        sb.append(String.format("%s,", isNotNull(pedagio)));
        sb.append(String.format("%s,", isNotNull(taxaEmbarque)));
        sb.append(String.format("%s,", isNotNull(seguro)));
        sb.append(String.format("%s,", isNotNull(outros)));
        sb.append(String.format("%s,", isNotNull(wirecardId)));
        sb.append(String.format("%s,", isNotNull(bpeOutros)));
        sb.append(String.format("%s,", isNotNull(bpeSeguro)));
        sb.append(String.format("%s,", isNotNull(bpeTarifa)));
        sb.append(String.format("%s,", isNotNull(bpePedagio)));
        sb.append(String.format("%s,", isNotNull(bpeDesconto)));
        sb.append(String.format("%s,", isNotNull(bpeValorPagar)));
        sb.append(String.format("%s,", isNotNull(bpeValorTotal)));
        sb.append(String.format("%s,", isNotNull(bpeTaxaEmbarque)));
        sb.append(String.format("%s,", isNotNull(bpeOutrosTributos)));
        sb.append(String.format("%s,", isNotNull(formaPagamento)));
       return sb.toString();
    }
    private String isNotNull(final Object value){
        if(null == value){
            return " - ";
        }else{
            return String.valueOf(value);
        }
    }
}
