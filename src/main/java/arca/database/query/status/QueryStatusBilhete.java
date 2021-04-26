package arca.database.query.status;

import arca.database.pojo.StatusBilhete;
import arca.database.postgre.AWSPostgres;
import arca.database.query.QueryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryStatusBilhete extends QueryModel {

    public QueryStatusBilhete(){
        super(new AWSPostgres());
    }


    public List<StatusBilhete> execute(final Long order) throws Exception{
        final String query = loadFromFile();
        PreparedStatement ps = null;
        final Connection conn = connection.create();
        final List<StatusBilhete> statusBilhetes = new ArrayList<>(0);
        try {
            ps = conn.prepareStatement(query);

            ps.setLong(1, order);

            final ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                statusBilhetes.add(inflate(resultSet));
            }
        }finally {
            if(null != conn){
                conn.close();
            }
            if(ps != null){
                ps.close();
            }
        }
        return statusBilhetes;
    }

    private StatusBilhete inflate(final ResultSet result) throws Exception{
        final StatusBilhete status = new StatusBilhete();

        status.servico = result.getString(result.findColumn("servico"));
        status.origem = result.getString(result.findColumn("origem"));
        status.destino = result.getString(result.findColumn("destino"));
        status.data = result.getString(result.findColumn("data"));
        status.poltrona = result.getString(result.findColumn("poltrona"));
        status.numBilhete = result.getString(result.findColumn("ticketNumber"));
        status.operadora = result.getLong(result.findColumn("operadora"));
        status.nome = result.getString(result.findColumn("nome"));
        status.rg = result.getString(result.findColumn("rg"));
        return status;
    }

    public void run() throws Exception{
        final String s = loadFromFile();
        System.out.println(s);
    }


}
