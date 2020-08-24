package arca.controllers.parse;

import arca.domain.entities.BuscaStatusBilhete;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;

public class BuscaStatusBilheteToJson implements ParseJson<BuscaStatusBilhete>{

    @Override
    public BuscaStatusBilhete parse(String json) throws ParseException {
        if(null == json || "".equals(json)){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, BuscaStatusBilhete.class);
        }
    }

    @Override
    public String transform(BuscaStatusBilhete buscaStatusBilhete) throws ParseException {
        if(null == buscaStatusBilhete){
            throw new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(buscaStatusBilhete);
        }
    }
}
