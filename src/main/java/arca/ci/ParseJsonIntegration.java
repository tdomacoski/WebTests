package arca.ci;

import arca.controllers.parse.ListaLocalidadeToJson;
import arca.controllers.parse.LocalidadeToJson;
import arca.controllers.parse.ParseJson;
import arca.controllers.parse.ResultListaLocalidadeToJson;
import arca.domain.entities.ListaLocalidade;
import arca.domain.entities.Localidade;
import arca.domain.entities.ResultListaLocalidade;

public class ParseJsonIntegration {
    public static ParseJson<Localidade> getParseLocalidade(){
        return new LocalidadeToJson();
    }

    public static ParseJson<ListaLocalidade> getParseListaLocalidade(){
        return new ListaLocalidadeToJson();
    }

    public static ParseJson<ResultListaLocalidade> getResultListaLocalidade(){
        return new ResultListaLocalidadeToJson();
    }
}
