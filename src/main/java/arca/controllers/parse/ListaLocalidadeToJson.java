package arca.controllers.parse;

import arca.domain.entities.ListaLocalidade;
import arca.exceptions.ParseException;
import arca.util.GsonUtil;
import arca.util.Logger;

public class ListaLocalidadeToJson implements ParseJson<ListaLocalidade>{
    @Override
    public ListaLocalidade parse(String json) throws ParseException {
        Logger.debug(json);
        if(null == json || "".equals(json.trim())){
            throw  new ParseException(json);
        }else{
            final ListaLocalidade l = GsonUtil.GSON.fromJson(json, ListaLocalidade.class);
            return l;
        }
    }

    @Override
    public String transform(final ListaLocalidade listaLocalidade) throws ParseException {
        if(null == listaLocalidade){
            throw new ParseException("null");
        }else{
            return GsonUtil.GSON.toJson(listaLocalidade);
        }
    }
}
