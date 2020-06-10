package arca.controllers.parse;

import arca.domain.entities.ListaLocalidade;
import arca.util.GsonUtil;

public class ListaLocalidadeToJson implements ParseJson<ListaLocalidade>{
    @Override
    public ListaLocalidade parse(String json) {
        if(null == json || "".equals(json.trim())){
            return null;
        }else{
            final ListaLocalidade l = GsonUtil.GSON.fromJson(json, ListaLocalidade.class);
            return l;
        }
    }

    @Override
    public String transform(ListaLocalidade listaLocalidade) {
        return null;
    }
}
