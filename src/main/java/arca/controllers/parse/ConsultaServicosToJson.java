package arca.controllers.parse;

import arca.domain.entities.ConsultaServicos;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;
import arca.util.Logger;

public class ConsultaServicosToJson implements ParseJson<ConsultaServicos> {

    @Override
    public ConsultaServicos parse(final String json) throws ParseException {
        Logger.debug(json);
        if(null == json || "".equals(json.trim())){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, ConsultaServicos.class);
        }
    }

    @Override
    public String transform(final ConsultaServicos consultaServicos) throws ParseException {
        if(null == consultaServicos){
            throw new ParseException("null");
        }
        return GsonUtil.GSON.toJson(consultaServicos);
    }
}
