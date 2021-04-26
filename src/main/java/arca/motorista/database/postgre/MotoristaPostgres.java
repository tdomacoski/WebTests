package arca.database.postgre;

import arca.database.ConnectionModel;

import java.sql.Connection;
import java.sql.DriverManager;

public class MotoristaPostgres implements ConnectionModel {
    @Override
    public Connection create() throws Exception {
        final String driver = "org.postgresql.Driver";
        final String user = "eogcyyctobrhxd";
        final String senha = "32a986e6a6bf43efe994e316ff054b5f03d05a757dda6c1abe9b4b1bbcf247d2";
        final String url = "postgres://eogcyyctobrhxd:32a986e6a6bf43efe994e316ff054b5f03d05a757dda6c1abe9b4b1bbcf247d2@ec2-52-200-111-186.compute-1.amazonaws.com:5432/dfk82dkv5nfp42";
        Class.forName(driver);
        return Connection.class.cast(DriverManager.getConnection(url, user, senha));
    }
}
