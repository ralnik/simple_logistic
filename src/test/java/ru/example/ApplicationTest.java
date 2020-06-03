package ru.example;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import ru.example.configuration.Config;
import ru.example.db.dao.LoadsDao;
import ru.example.db.dao.LoadsDaoImpl;
import ru.example.db.dao.LocationDao;
import ru.example.db.dao.LocationDaoImpl;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class ApplicationTest
{
    /**
     * Rigorous Test :-)
     */
    @Before
    public void createTableTest() {
        File file = new File(Config.getProperty("sqlite.name"));
        if (!file.exists()) {
            LocationDao locationDao = new LocationDaoImpl();
            locationDao.createTable();

            LoadsDao loadsDao = new LoadsDaoImpl();
            loadsDao.createTable();
            loadsDao.createViews();
        }
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
}
