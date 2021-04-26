package main;

import arca.domain.entities.Localidade;
import arca.domain.entities.Operadora;
import arca.logger.Logger;
import arca.logger.LoggerFile;

public class PesquisaNomesCidades {




    private final Operadora operadora;
    private final Logger logger;

    PesquisaNomesCidades(final Operadora operadora){
        this.operadora = operadora;
        this.logger = new LoggerFile(String.format("%s_%s", operadora.nome, getClass().getSimpleName()));
    }

    private void execute(final String origem, final String destino) throws Exception {




    }



    public static void main(String[] args) throws Exception {


    }
}
