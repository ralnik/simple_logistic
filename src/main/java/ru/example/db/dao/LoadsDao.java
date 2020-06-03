package ru.example.db.dao;

import ru.example.db.entities.Loads;
import ru.example.db.response.CountCargoesInItemResponse;
import ru.example.db.response.CountCargoesToExportXmlResponse;

import java.util.List;

public interface LoadsDao extends CommonDao<Loads> {
    void createViews();
    List<Loads> findByLocationId(Long id);
    List<CountCargoesInItemResponse> getCountCargoesInItem(String listLocation);
    List<CountCargoesToExportXmlResponse> getCountCargoesToExportXml(String listLocation);

}
