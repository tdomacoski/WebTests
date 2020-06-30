package arca.controllers.parse;

import arca.domain.entities.ResultListaLocalidade;
import arca.util.GsonUtil;
import arca.util.Logger;

public class ResultListaLocalidadeToJson implements ParseJson<ResultListaLocalidade> {

    @Override
    public ResultListaLocalidade parse(String json) {
        Logger.debug(json);
        if(null == json || "".equals(json.trim())){
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, ResultListaLocalidade.class);
        }
    }

    @Override
    public String transform(ResultListaLocalidade resultListaLocalidade) {
        if(null == resultListaLocalidade){
            return EMPTY;
        }else{
           return GsonUtil.GSON.toJson(resultListaLocalidade);
        }
    }
}
