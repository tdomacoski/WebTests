package arca.controllers.parse;

import arca.domain.entities.BloquearPoltrona;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;
import arca.util.Logger;

public class BloquearPoltronaToJson implements ParseJson<BloquearPoltrona> {

    @Override
    public BloquearPoltrona parse(final String json) throws ParseException {
        Logger.debug(json);
        if(null == json || "".equals(json)){
            throw new ParseException(json);
        }else{
            return GsonUtil.GSON.fromJson(json, BloquearPoltrona.class);
        }
    }

    @Override
    public String transform(final BloquearPoltrona bloquearPoltrona) throws ParseException {
        if(null == bloquearPoltrona){
            throw new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(bloquearPoltrona);
        }
    }
}
