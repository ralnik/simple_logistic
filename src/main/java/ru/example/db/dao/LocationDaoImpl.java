package ru.example.db.dao;

import lombok.SneakyThrows;
import ru.example.configuration.JdbcTemplate;
import ru.example.db.entities.Location;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationDaoImpl implements LocationDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public void createTable() {
        String sql = "CREATE TABLE location ( \n" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "name TEXT \n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    @SneakyThrows
    @Override
    public Location save(Location location) {
        String sql = "INSERT INTO location(name) VALUES('%s')";
        jdbcTemplate.execute(String.format(sql, location.getName()));
        String sqlLastInsertRecord = "SELECT seq as id from sqlite_sequence where name='location'";
        ResultSet rs = jdbcTemplate.select(sqlLastInsertRecord);
        Location resultLocation = null;
        while (rs.next()) {
            resultLocation = findOne(rs.getLong("id"));
        }
        return resultLocation;
    }

    @Override
    public void update(Location location) {
        String sql = "UPDATE location SET name='%s' where id=%s";
        jdbcTemplate.execute(String.format(sql,
                location.getName(),
                location.getId()));
    }

    @Override
    public void delete(Location location) {
        String sql = "DELETE FROM location where id=%s";
        jdbcTemplate.execute(String.format(sql, location.getId()));
    }

    @SneakyThrows
    @Override
    public List<Location> findAll() {
        String sql = "select * from location";
        List<Location> list = new ArrayList<>();
        CachedRowSet rs = jdbcTemplate.select(sql);
        while (rs.next()) {
            Location location = new Location();
            location.setId(rs.getLong("id"));
            location.setName(rs.getString("name"));
            list.add(location);
        }
        return list;
    }

    @SneakyThrows
    @Override
    public Location findOne(Long id) {
        String sql = "select * from location where id=%s";
        Location location = new Location();
        ResultSet rs = jdbcTemplate.select(String.format(sql, id));
        while (rs.next()) {
            location.setId(rs.getLong("id"));
            location.setName(rs.getString("name"));
        }
        return location;
    }
}
