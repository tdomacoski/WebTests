package arca.controllers.parse;

import arca.domain.entities.DevolvePoltrona;
import arca.util.GsonUtil;

public class DevolvePoltronaToJson implements ParseJson<DevolvePoltrona> {

    @Override
    public DevolvePoltrona parse(final String json) {
        if(null == json || "".equals(json)) {
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, DevolvePoltrona.class);
        }
    }

    @Override
    public String transform(final DevolvePoltrona devolvePoltrona) {
        if(null == devolvePoltrona){
            return "";
        }else{
            return GsonUtil.GSON.toJson(devolvePoltrona);
        }
    }
}
