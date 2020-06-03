package ru.example.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import ru.example.db.dao.LoadsDao;
import ru.example.db.dao.LoadsDaoImpl;
import ru.example.db.dao.LocationDao;
import ru.example.db.dao.LocationDaoImpl;
import ru.example.db.entities.Loads;
import ru.example.db.entities.Location;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class LoadsTest {
    private static final String LOCATION_NAME = "testNameLocation" + System.currentTimeMillis();
    private static final String LOADS_NAME = "testNameLoads" + System.currentTimeMillis();

    private LocationDao locationDao = new LocationDaoImpl();
    private Location locationTest = new Location();


    private LoadsDao loadsDao = new LoadsDaoImpl();
    private Loads loadTest = new Loads();

    @Before
    public void setUp() {
        locationTest.setName(LOCATION_NAME);
        locationTest = locationDao.save(locationTest);

        loadTest.setName(LOADS_NAME);
        loadTest.setLocation(locationTest);
        loadTest = loadsDao.save(loadTest);
    }

    @Test
    public void findAllTest() {
        List<Loads> loads = loadsDao.findAll();

        assertNotNull(loads);
        assertTrue(loads.size() > 0);
    }

    @Test
    public void findOneTest() {
        Loads load = loadsDao.findOne(loadTest.getId());

        assertNotNull(load);
        assertEquals(LOADS_NAME, load.getName());
        assertEquals(loadTest.getId(), load.getId());
    }

    @Test
    public void findByLocationIdTest() {
        List<Loads> loads = loadsDao.findByLocationId(loadTest.getLocation().getId());

        assertNotNull(loads);
        assertTrue(loads.size() > 0);
        assertEquals(LOADS_NAME, loads.get(0).getName());
        assertEquals(LOCATION_NAME, loads.get(0).getLocation().getName());
        assertEquals(locationTest, loads.get(0).getLocation());
    }

    @Test
    public void updateTest() {
        Loads load = loadTest;
        load.setName(LOADS_NAME + "test");
        loadsDao.update(load);

        Loads loads1 = loadsDao.findOne(load.getId());

        assertNotNull(loads1);
        assertEquals(LOADS_NAME+"test", loads1.getName());
        assertEquals(locationTest, loads1.getLocation());
    }

    @Test
    public void deleteTest() {
        loadsDao.delete(loadTest);

        Loads load = loadsDao.findOne(loadTest.getId());

        assertNull(load.getId());
        assertNull(load.getName());
        assertNull(load.getLocation());
    }
}
