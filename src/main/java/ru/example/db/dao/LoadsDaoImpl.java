package ru.example.db.dao;

import lombok.SneakyThrows;
import ru.example.configuration.JdbcTemplate;
import ru.example.db.entities.Loads;
import ru.example.db.entities.Location;
import ru.example.db.response.CountCargoesInItemResponse;
import ru.example.db.response.CountCargoesToExportXmlResponse;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LoadsDaoImpl implements LoadsDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate();
    private LocationDao locationDao = new LocationDaoImpl();

    @Override
    public void createTable() {
        String sql = "CREATE TABLE loads ( \n" +
                "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                "name TEXT, \n" +
                "loc_id INTEGER \n" +
                ");";
        jdbcTemplate.execute(sql);
    }

    @Override
    public void createViews() {
        String sql = "CREATE VIEW 'countLoadByLocation' as\n" +
                "select location.name as locationName, location.id as locationId, \n" +
                "loads.name as loadsName, loads.id as loadsId \n" +
                "from location JOIN loads on loads.loc_id = location.id";
        jdbcTemplate.execute(sql);
    }

    @SneakyThrows
    @Override
    public Loads save(Loads loads) {
        String sql = "INSERT INTO loads(name, loc_id) VALUES ('%s', %s)";
        jdbcTemplate.execute(String.format(sql, loads.getName(), loads.getLocation().getId()));
        String sqlLastInsertRecord = "SELECT seq as id from sqlite_sequence where name='loads'";
        ResultSet rs = jdbcTemplate.select(sqlLastInsertRecord);
        Loads resultLoads = null;
        while(rs.next()) {
            resultLoads = findOne(rs.getLong("id"));
        }
        return resultLoads;
    }

    @Override
    public void update(Loads loads) {
        String sql = "UPDATE loads set id=%s, name='%s', loc_id=%s where id=%s";
        jdbcTemplate.execute(String.format(sql,
                loads.getId(),
                loads.getName(),
                loads.getLocation().getId(),
                loads.getId()));
    }

    @Override
    public void delete(Loads loads) {
        String sql = "DELETE FROM loads where id=%s";
        jdbcTemplate.execute(String.format(sql, loads.getId()));
    }

    @SneakyThrows
    @Override
    public List<Loads> findAll() {
        String sql = "select * from loads";
        ResultSet rs = jdbcTemplate.select(sql);
        List<Loads> list = new ArrayList<>();
        while (rs.next()) {
            Loads loads = new Loads();
            loads.setId(rs.getLong("id"));
            loads.setName(rs.getString("name"));
            Location location = locationDao.findOne(rs.getLong("loc_id"));
            loads.setLocation(location);
            list.add(loads);
        }
        return list;
    }

    @SneakyThrows
    @Override
    public Loads findOne(Long id) {
        String sql = "select * from loads where id=%s";
        ResultSet rs = jdbcTemplate.select(String.format(sql, id));
        Loads loads = new Loads();
        while (rs.next()) {
            loads.setId(rs.getLong("id"));
            loads.setName(rs.getString("name"));
            Location location = locationDao.findOne(rs.getLong("loc_id"));
            loads.setLocation(location);
        }
        return loads;
    }

    @SneakyThrows
    @Override
    public List<CountCargoesInItemResponse> getCountCargoesInItem(String listLocation) {
        String sql = null;
        ResultSet rs = null;
        if (listLocation != null) {
            sql = "select locationName, locationId, loadsName, loadsId from countLoadByLocation where locationName in (%s)";
            String[] mas = listLocation.split(",");
            String inSql = "";
            for (String item : mas) {
                if (inSql.isEmpty()) {
                    inSql = "'" + item.trim() + "'";
                } else {
                    inSql = inSql + ",'" + item.trim() + "'";
                }
            }
            rs = jdbcTemplate.select(String.format(sql, inSql));
        } else {
            sql = "select locationName, locationId, loadsName, loadsId from countLoadByLocation";
            rs = jdbcTemplate.select(sql);
        }
        List<CountCargoesInItemResponse> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new CountCargoesInItemResponse(
                    rs.getString("locationName"), rs.getInt("locationId"),
                    rs.getString("loadsName"), rs.getInt("loadsId")));
        }
        return list;
    }

    @SneakyThrows
    @Override
    public List<CountCargoesToExportXmlResponse> getCountCargoesToExportXml(String listLocation) {
        String sql = null;
        ResultSet rs = null;
        if (listLocation != null) {
            sql = "select id, name from location where name in (%s)";
            String[] mas = listLocation.split(",");
            String inSql = "";
            for (String item : mas) {
                if (inSql.isEmpty()) {
                    inSql = "'" + item.trim() + "'";
                } else {
                    inSql = inSql + ",'" + item.trim() + "'";
                }
            }
            rs = jdbcTemplate.select(String.format(sql, inSql));
        } else {
            sql = "select id, name from location";
            rs = jdbcTemplate.select(sql);
        }

        List<CountCargoesToExportXmlResponse> list = new ArrayList<>();
        while (rs.next()) {
            CountCargoesToExportXmlResponse response = new CountCargoesToExportXmlResponse();
            response.setLocationName(rs.getString("name"));
            response.setLocationId(rs.getLong("id"));
            List<Loads> loads = findByLocationId(response.getLocationId());
            response.setLoads(loads);
            list.add(response);
        }
        return list;
    }

    @SneakyThrows
    @Override
    public List<Loads> findByLocationId(Long id) {
        String sql = "select * from loads where loc_id=%s";
        List<Loads> list = new ArrayList<>();
        ResultSet rs = jdbcTemplate.select(String.format(sql, id));
        while (rs.next()) {
            Loads loads = new Loads();
            loads.setId(rs.getLong("id"));
            loads.setName(rs.getString("name"));
            loads.setLocation(locationDao.findOne(id));
            list.add(loads);
        }
        return list;
    }
}
