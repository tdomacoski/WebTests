package arca.controllers.parse;

import arca.domain.entities.ConsultaServicos;
import arca.util.GsonUtil;

public class ConsultaServicosToJson implements ParseJson<ConsultaServicos> {

    @Override
    public ConsultaServicos parse(final String json) {
        if(null == json || "".equals(json.trim())){
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, ConsultaServicos.class);
        }
    }

    @Override
    public String transform(final ConsultaServicos consultaServicos) {
        if(null == consultaServicos){
            return "";
        }
        return GsonUtil.GSON.toJson(consultaServicos);
    }
}
