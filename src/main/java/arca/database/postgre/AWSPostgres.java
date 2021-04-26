package arca.database.postgre;

import arca.database.ConnectionModel;

import java.sql.Connection;
import java.sql.DriverManager;

public class AWSPostgres  implements ConnectionModel {
    @Override
    public Connection create() throws Exception {
        final String driver = "org.postgresql.Driver";
        final String user = "u1odn26ao3e339";
        final String senha = "pfa97a07b21e7aff910517348ea2337bc4acd635ea5a69626126cfac37be12f93";
        final String url = "jdbc:postgresql://ec2-35-173-171-60.compute-1.amazonaws.com:5432/db36o4lo13db32";
        Class.forName(driver);
        return Connection.class.cast(DriverManager.getConnection(url, user, senha));
    }
}
