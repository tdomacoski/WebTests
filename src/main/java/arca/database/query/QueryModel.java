package arca.database.query;

import arca.database.ConnectionModel;
import arca.database.query.read.ReadSQLQuery;

public abstract class QueryModel {
    protected final ConnectionModel connection;

    public QueryModel(final ConnectionModel connection){
        this.connection = connection;
    }


    protected String loadFromFile()throws Exception{
        return ReadSQLQuery.read(getClass().getSimpleName());
    }
}
