package ru.example.xml;

import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;
import ru.example.db.entities.Loads;
import ru.example.db.entities.Location;
import ru.example.db.response.CountCargoesToExportXmlResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CountCargoesXmlParserTest {
    private XmlParser parser;
    private File file;
    private CountCargoesToExportXmlResponse response;
    private List<CountCargoesToExportXmlResponse> listCountCargoes;

    @Before
    public void setUP() {

        parser = new CountCargoesXmlParser("test.xml");
        response = new CountCargoesToExportXmlResponse();
        response.setLocationName("test_location");
        response.setLocationId(1L);

        Location testLocation = new Location();
        testLocation.setId(1L);
        testLocation.setName("location 1");

        List<Loads> loadsList = new ArrayList<>();
        Loads load = new Loads();
        load.setId(1L);
        load.setLocation(testLocation);
        load.setName("test load1");
        loadsList.add(load);

        Loads load2 = new Loads();
        load2.setId(2L);
        load2.setLocation(testLocation);
        load2.setName("test load2");
        loadsList.add(load2);

        response.setLoads(loadsList);
        listCountCargoes = new ArrayList<>();
        listCountCargoes.add(response);


    }

    @SneakyThrows
    @Test
    public void saveObjectTest() {
        parser.exportXml(listCountCargoes);
    }

}
