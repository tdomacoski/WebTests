package arca.controllers.parse;

import arca.domain.entities.ResultListaLocalidade;
import arca.util.GsonUtil;

public class ResultListaLocalidadeToJson implements ParseJson<ResultListaLocalidade> {

    @Override
    public ResultListaLocalidade parse(String json) {
        if(null == json || "".equals(json.trim())){
            return null;
        }else{
            return GsonUtil.GSON.fromJson(json, ResultListaLocalidade.class);
        }
    }

    @Override
    public String transform(ResultListaLocalidade resultListaLocalidade) {
        if(null == resultListaLocalidade){
            return "";
        }else{
           return GsonUtil.GSON.toJson(resultListaLocalidade);
        }
    }
}
