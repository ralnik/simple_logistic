package ru.example.xml;

import ru.example.db.response.CommonXMLRoot;

import java.util.List;

public interface XmlParser<T extends CommonXMLRoot> {
    T importXml();
    void exportXml(List<T> object);
}
