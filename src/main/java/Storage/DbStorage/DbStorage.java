package Storage.DbStorage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DbStorage {
    protected Connection connection;

    protected DbStorage(){
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DbConst.DATABASE_URL,DbConst.USERNAME,DbConst.PASSWORD);
            connection.prepareStatement("set search_path to 'Blog'").execute();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}
