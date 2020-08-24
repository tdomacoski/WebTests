package arca.domain.entities;

public class BilheteInfo extends JsonModel {

//    CORRIDA AGRUPADA - Provavél não
//    TRANSFERENCIA PASSAGEM - transferiu
//    BOLETO ENTREGADO - IMPRESSO
//    CHECKIN - NãO
//    DEVOLUCAO - NãO
//    CONTINGENCIA 100% - CONFIGURAÇÃO da empresa (talvez seja possível devolver)
//    Em aberto - Pode Devolver 

    public String numBilhete;
    public String status;
}