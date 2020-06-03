package ru.example.controller;

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

@RunWith(JUnit4.class)
public class CreateNewCargoesControllerTest {
    private LocationDao locationDao = new LocationDaoImpl();
    private LoadsDao loadsDao = new LoadsDaoImpl();

    private static final String LOCATIONS = "location" + System.currentTimeMillis();
    private static final Integer COUNT_CARGOES = 3;

    @Before
    public void setUp(){

    }

    @Test
    public void CreateNewCargoesTest() {
        Location location = new Location();
        location.setName(LOCATIONS);
        location = locationDao.save(location);

        for (int item = 1; item <= COUNT_CARGOES; item++) {
            Loads loads = new Loads();
            loads.setName("AAAA." + location.getId() + "." + item);
            loads.setLocation(location);
            loadsDao.save(loads);
        }
    }
}
