package arca.database.query.cancelamento;

import arca.database.pojo.CancelamentoBilhete;
import arca.database.postgre.AWSPostgres;
import arca.database.query.QueryModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QueryCancelamentoByOrder extends QueryModel {

    public QueryCancelamentoByOrder() {
        super(new AWSPostgres());
    }


    public List<CancelamentoBilhete> execute(final Long order)throws Exception {
        final String query = loadFromFile();

        System.out.println(query);

        PreparedStatement ps = null;
        final Connection conn = connection.create();

        final List<CancelamentoBilhete> cancelamentoBilhetes = new ArrayList<>(0);
        try {
            ps = conn.prepareStatement(query);

            ps.setLong(1, order);

            final ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                cancelamentoBilhetes.add(inflate(resultSet));
            }
        } finally {
            if (null != conn) {
                conn.close();
            }
            if (ps != null) {
                ps.close();
            }
            return cancelamentoBilhetes;
        }
    }


    private CancelamentoBilhete inflate ( final ResultSet result) throws Exception{
        final CancelamentoBilhete bilhete = new CancelamentoBilhete();
        bilhete.ordem_id = result.getString(result.findColumn("ordem_id"));
        bilhete.origem = result.getString(result.findColumn("origem"));
        bilhete.destino = result.getString(result.findColumn("destino"));
        bilhete.data = result.getString(result.findColumn("data"));
        bilhete.servico = result.getString(result.findColumn("servico"));
        bilhete.operadora = result.getString(result.findColumn("operadora"));
        bilhete.transacao_id = result.getString(result.findColumn("transacao_id"));
        bilhete.bilhete = result.getString(result.findColumn("bilhete"));
        bilhete.poltrona = result.getString(result.findColumn("poltrona"));
        bilhete.token = result.getString(result.findColumn("token"));
        bilhete.nome = result.getString(result.findColumn("nome"));
        bilhete.rg = result.getString(result.findColumn("rg"));
        return bilhete;
    }


}
