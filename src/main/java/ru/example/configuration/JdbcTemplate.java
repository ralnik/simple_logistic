package ru.example.configuration;

import com.sun.rowset.CachedRowSetImpl;
import lombok.Data;
import lombok.SneakyThrows;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.Statement;

@Data
public class JdbcTemplate {
    private Jdbc jdbc;

    public JdbcTemplate() {
        jdbc = new Jdbc(Config.getProperty("sqlite.driver"), Config.getProperty("sqlite.name"));
        jdbc.conn();
    }

    @SneakyThrows
    public CachedRowSet select(String sql) {
        Connection connection = jdbc.getConnection();
        try (Statement statement = connection.createStatement()) {
            CachedRowSet cs = new CachedRowSetImpl();
            cs.populate(statement.executeQuery(sql));
            return cs;
        }
    }

    @SneakyThrows
    public void execute(String sql) {
        try (Connection connection = jdbc.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
