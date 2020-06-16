package arca.controllers.parse;

import arca.domain.entities.ConfirmacaoVendaResult;
import arca.util.GsonUtil;

public class ConfirmacaoVendaToJson implements ParseJson<ConfirmacaoVendaResult> {
    @Override
    public ConfirmacaoVendaResult parse(final String json) {
        if(null == json || "".equals(json)){
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, ConfirmacaoVendaResult.class);
        }
    }
    @Override
    public String transform(final ConfirmacaoVendaResult confirmacaoVendaResult) {
        if(confirmacaoVendaResult == null){
            return "";
        }else{
            return GsonUtil.GSON.toJson(confirmacaoVendaResult);
        }
    }
}
