package main;

import arca.ci.ApiIntegration;
import arca.ci.OperadoraIntegration;
import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;

import java.util.List;

public class MatinhosCaioba {
//    {
//        "id": 12957,
//            "cidade": "MATINHOS ",
//            "uf": "PR"
//    },

//    {
//        "id": 21695,
//            "cidade": "CAIOBA ",
//            "uf": "PR"
//    }

    static final Operadora graciosa = OperadoraIntegration.graciosa();
    static final Logger logger = new LoggerFile("matinhos_X_caioba_graciosa");
    public static void main(String[] args) {

        try {





        }catch (final Exception e){
            e.printStackTrace();
        }
    }

    private static Localidade byId(Long id) throws  Exception{
        for(final Localidade l : ApiIntegration.getOrigens(graciosa, logger)){
            if(id.equals(l.id)){
                return l;
            }
        }
        return null;
    }
}
