package arca.database.query.status;

import arca.database.query.QueryModel;

public class QueryStatusBilhete extends QueryModel {

    public void run() throws Exception{
        final String s = loadFromFile();
        System.out.println(s);
    }


}
