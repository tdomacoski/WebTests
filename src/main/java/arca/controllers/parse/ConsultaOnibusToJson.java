package arca.controllers.parse;

import arca.domain.entities.ConsultaOnibus;
import arca.domain.entities.ResultadoViagem;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;
import arca.util.Logger;

public class ConsultaOnibusToJson implements ParseJson<ConsultaOnibus> {

    @Override
    public ConsultaOnibus parse(String json) throws ParseException {
        Logger.debug(json);
        if(null == json || "".equals(json)){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, ConsultaOnibus.class);
        }
    }

    @Override
    public String transform(ConsultaOnibus consultaOnibus) throws ParseException {
        if(null == consultaOnibus){
            throw new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(consultaOnibus);
        }
    }
}
