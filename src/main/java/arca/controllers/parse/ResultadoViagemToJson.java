package arca.controllers.parse;

import arca.domain.entities.ResultadoViagem;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;

public class ResultadoViagemToJson implements ParseJson<ResultadoViagem>{

    @Override
    public ResultadoViagem parse(String json) throws ParseException {
        if(null == json || "".equals(json)){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, ResultadoViagem.class);
        }
    }

    @Override
    public String transform(ResultadoViagem resultadoViagem) throws ParseException {
        if(null == resultadoViagem){
            throw new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(resultadoViagem);
        }
    }
}
