package arca.database.query.cancelamento;

import arca.database.ConnectionModel;
import arca.database.pojo.CancelamentoBilhete;
import arca.database.postgre.AWSPostgres;
import arca.database.query.QueryModel;

import java.util.List;

public class QueryCancelamentoByOrder extends QueryModel {

    public QueryCancelamentoByOrder() {
        super(new AWSPostgres());
    }


    public List<CancelamentoBilhete> execute(final Long order)throws Exception{

    }


}
