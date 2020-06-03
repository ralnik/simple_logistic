package ru.example.configuration;

import lombok.Data;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Data
public class Jdbc {
    private final String driver;
    private final String db_name;

    private Connection connection;
    private Statement statement;

    public Connection getConnection() {
        try {
             return DriverManager.getConnection(driver + ":" + db_name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public void conn(){
        connection = DriverManager.getConnection(driver + ":" + db_name);
        statement = connection.createStatement();
    }

    @SneakyThrows
    public void closeAll() {
        connection.close();
        statement.close();
    }

}
