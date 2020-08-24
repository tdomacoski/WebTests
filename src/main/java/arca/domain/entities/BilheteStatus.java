package arca.domain.entities;

public enum  BilheteStatus {
    CORRIDA_AGRUPADA("CORRIDA AGRUPADA"),
    TRANSFERENCIA_PASSAGEM("TRANSFERENCIA PASSAGEM"),
    BOLETO_ENTREGADO("BOLETO ENTREGADO"),
    CHECKIN("CHECKIN"), DEVOLUCAO("DEVOLUCAO"),
    CONTINGENCIA("CONTINGENCIA 100%"),
    EM_ABERTO("Em aberto");

    public String value;
    BilheteStatus(final String val){
        value = val;
    }
}
