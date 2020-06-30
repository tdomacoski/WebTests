package arca.controllers.parse;

import arca.domain.entities.Localidade;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;
import arca.util.Logger;

public class LocalidadeToJson implements ParseJson<Localidade> {

    @Override
    public Localidade parse(final String json) throws ParseException {
        Logger.debug(json);
        if (null == json || "".equals(json.trim())) {
            throw new ParseException(json);
        } else {
            return GsonUtil.GSON.fromJson(json, Localidade.class);
        }
    }

    @Override
    public String transform(final Localidade localidade) throws ParseException {
        if (null == localidade) {
            throw new ParseException("null");
        } else {
            return GsonUtil.GSON.toJson(localidade);
        }
    }
}
