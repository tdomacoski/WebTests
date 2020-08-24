package arca.controllers.parse;

import arca.domain.entities.DevolvePoltrona;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;

public class DevolvePoltronaToJson implements ParseJson<DevolvePoltrona> {

    @Override
    public DevolvePoltrona parse(final String json) throws ParseException {
        if (null == json || "".equals(json)) {
            throw new ParseException(json);
        } else {
            return GsonUtil.GSON.fromJson(json, DevolvePoltrona.class);
        }
    }

    @Override
    public String transform(final DevolvePoltrona devolvePoltrona) throws ParseException {
        if (null == devolvePoltrona) {
            throw new ParseException("null");
        } else {
            return GsonUtil.GSON.toJson(devolvePoltrona);
        }
    }
}
