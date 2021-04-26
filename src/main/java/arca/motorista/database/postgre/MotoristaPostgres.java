package arca.motorista.database.postgre;

import arca.database.ConnectionModel;

import java.sql.Connection;
import java.sql.DriverManager;

public class MotoristaPostgres implements ConnectionModel {
    @Override
    public Connection create() throws Exception {
        final String driver = "org.postgresql.Driver";
        final String user = "uizjboorumoobe";
        final String senha = "c4c930453cc2454f6c8b54c39de26d459d0bab8f324b8bf987ebe3603f34c4b6";
        final String url = "postgres://uizjboorumoobe:c4c930453cc2454f6c8b54c39de26d459d0bab8f324b8bf987ebe3603f34c4b6@ec2-34-235-240-133.compute-1.amazonaws.com:5432/dq10qv9rtc4ol";
        Class.forName(driver);
        return Connection.class.cast(DriverManager.getConnection(url, user, senha));
    }
}
