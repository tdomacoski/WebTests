package arca.controllers.parse;

import arca.domain.entities.Localidade;
import arca.util.GsonUtil;

public class LocalidadeToJson implements ParseJson<Localidade> {

    @Override
    public Localidade parse(final String json) {
        if (null == json || "".equals(json.trim())) {
            return null;
        } else {
            return GsonUtil.GSON.fromJson(json, Localidade.class);
        }
    }

    @Override
    public String transform(final Localidade localidade) {
        if (null == localidade) {
            return "";
        } else {
            return GsonUtil.GSON.toJson(localidade);
        }
    }
}
