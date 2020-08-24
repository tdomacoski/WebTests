package arca.controllers.parse;

import arca.domain.entities.ConfirmacaoVendaResult;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;

public class ConfirmacaoVendaToJson implements ParseJson<ConfirmacaoVendaResult> {
    @Override
    public ConfirmacaoVendaResult parse(final String json) throws ParseException {
        if(null == json || "".equals(json)){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, ConfirmacaoVendaResult.class);
        }
    }
    @Override
    public String transform(final ConfirmacaoVendaResult confirmacaoVendaResult)throws ParseException {
        if(confirmacaoVendaResult == null){
            throw  new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(confirmacaoVendaResult);
        }
    }
}
