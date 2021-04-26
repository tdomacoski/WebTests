package arca.database;

import java.sql.Connection;

public interface ConnectionModel {

    Connection create() throws Exception;

}
