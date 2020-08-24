package arca.controllers.parse;

import arca.domain.entities.ConsultaOnibus;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;

public class ConsultaOnibusToJson implements ParseJson<ConsultaOnibus> {

    @Override
    public ConsultaOnibus parse(String json) throws ParseException {
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
