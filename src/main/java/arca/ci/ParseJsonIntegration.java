package arca.ci;

import arca.controllers.parse.*;
import arca.domain.entities.*;

/**
 * Classe possui o Singleton referente aos parses.
 */
public class ParseJsonIntegration {

    private static LocalidadeToJson localidadeToJson;
    private static ListaLocalidadeToJson listaLocalidadeToJson;
    private static ResultListaLocalidadeToJson resultListaLocalidadeToJson;
    private static ConsultaServicosToJson consultaServicosToJson;
    private static BloquearPoltronaToJson bloquearPoltronaToJson;
    private static ConfirmacaoVendaToJson confirmacaoVendaToJson;
    private static DevolvePoltronaToJson devolvePoltronaToJson;
    private static ResultadoViagemToJson resultadoViagemToJson;
    private static ConsultaOnibusToJson consultaOnibusToJson;

    public static ParseJson<Localidade> getParseLocalidade(){
        if(null == localidadeToJson){
            localidadeToJson = new LocalidadeToJson();
        }
        return localidadeToJson;
    }

    public static ParseJson<ListaLocalidade> getParseListaLocalidade(){
        if(null == listaLocalidadeToJson){
            listaLocalidadeToJson =  new ListaLocalidadeToJson();
        }
        return listaLocalidadeToJson;
    }

    public static ParseJson<ResultListaLocalidade> getResultListaLocalidade(){
        if(null == resultListaLocalidadeToJson){
            resultListaLocalidadeToJson = new ResultListaLocalidadeToJson();
        }
        return resultListaLocalidadeToJson;
    }

    public static ParseJson<ConsultaServicos> getParseConsultaServicos(){
        if(null ==  consultaServicosToJson){
            consultaServicosToJson = new ConsultaServicosToJson();
        }
        return consultaServicosToJson;
    }

    public static ParseJson<ResultadoViagem> getParseResultadoViagem(){
        if(resultadoViagemToJson == null){
            resultadoViagemToJson = new ResultadoViagemToJson();
        }
        return resultadoViagemToJson;
    }
    public static ParseJson<BloquearPoltrona> getBloquearPoltrona(){
        if(null == bloquearPoltronaToJson){
            bloquearPoltronaToJson = new BloquearPoltronaToJson();
        }
        return bloquearPoltronaToJson;
    }

    public static ParseJson<ConfirmacaoVendaResult> getConfirmacaoVendaResult(){
        if(null == confirmacaoVendaToJson){
            confirmacaoVendaToJson = new ConfirmacaoVendaToJson();
        }
        return confirmacaoVendaToJson;
    }

    public static ParseJson<DevolvePoltrona> getDevolvePoltronaToJson(){
        if(null == devolvePoltronaToJson){
            devolvePoltronaToJson = new DevolvePoltronaToJson();
        }
        return devolvePoltronaToJson;
    }


    public static ParseJson<ConsultaOnibus> getConsultaOnibusToJson(){
        if(null == consultaOnibusToJson){
            consultaOnibusToJson = new ConsultaOnibusToJson();
        }
        return consultaOnibusToJson;
    }



}
