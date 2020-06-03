package ru.example.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.example.db.dao.LocationDao;
import ru.example.db.dao.LocationDaoImpl;
import ru.example.db.entities.Location;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LocationDaoTest {
    private static final String LOCATION_NAME = "testname" + System.currentTimeMillis();
    private LocationDao locationDao = new LocationDaoImpl();
    private Location locationTest = new Location();

    @Before
    public void setUp() {
        locationTest.setName(LOCATION_NAME);
        locationTest = locationDao.save(locationTest);
    }

    @Test
    public void findAllTest() {
        List<Location> list = locationDao.findAll();

        assertNotNull(list);
        assertTrue(list.size() > 0);
    }

    @Test
    public void findOneTest() {
        Location location = locationDao.findOne(locationTest.getId());

        assertNotNull(location);
        assertEquals(locationTest.getName(), location.getName());
        assertEquals(locationTest.getId(), location.getId());
        assertEquals(LOCATION_NAME, location.getName());
    }

    @Test
    public void updateTest() {
        Location location = locationTest;
        location.setName(LOCATION_NAME + "test");
        locationDao.update(location);

        Location location1 = locationDao.findOne(location.getId());

        assertNotNull(location1);
        assertEquals(location.getName(), location1.getName());
        assertEquals(location.getId(), location1.getId());
        assertEquals(LOCATION_NAME+"test", location1.getName());
    }

    @Test
    public void deleteTest() {
        locationDao.delete(locationTest);

        Location location = locationDao.findOne(locationTest.getId());
        assertNull(location.getId());
        assertNull(location.getName());
    }
}
