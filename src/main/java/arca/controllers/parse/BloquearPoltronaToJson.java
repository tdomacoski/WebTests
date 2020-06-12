package arca.controllers.parse;

import arca.domain.entities.BloquearPoltrona;
import arca.util.GsonUtil;

public class BloquearPoltronaToJson implements ParseJson<BloquearPoltrona> {

    @Override
    public BloquearPoltrona parse(final String json) {
        if(null == json){
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, BloquearPoltrona.class);
        }
    }

    @Override
    public String transform(final BloquearPoltrona bloquearPoltrona) {
        if(null == bloquearPoltrona){
            return EMPTY;
        }else{
            return GsonUtil.GSON.toJson(bloquearPoltrona);
        }
    }
}
