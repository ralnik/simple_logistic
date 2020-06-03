package ru.example.xml;


import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.example.db.entities.Loads;
import ru.example.db.response.CountCargoesToExportXmlResponse;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.util.List;

public class CountCargoesXmlParser implements XmlParser<CountCargoesToExportXmlResponse> {
    private DocumentBuilderFactory dbf;
    private DocumentBuilder db ;
    private Document doc;
    private String filename;

    @SneakyThrows
    public CountCargoesXmlParser(String filename) {
        this.filename = filename;
        dbf = DocumentBuilderFactory.newInstance();
        db  = dbf.newDocumentBuilder();
        doc = db.newDocument();
    }

    @Override
    public CountCargoesToExportXmlResponse importXml() {
        return null;
    }

    @Override
    public void exportXml(List<CountCargoesToExportXmlResponse> list) {
        Element root = doc.createElement("dbinfo");

        for (CountCargoesToExportXmlResponse response : list){
            Element locacationElement = doc.createElement("location");
            locacationElement.setAttribute("name", response.getLocationName());
            locacationElement.setAttribute("id", response.getLocationId().toString());

            for (Loads load : response.getLoads()) {
                Element loadElement = doc.createElement("load");
                loadElement.setAttribute("name", load.getName());
                loadElement.setAttribute("id", load.getId().toString());
                locacationElement.appendChild(loadElement);
            }
            root.appendChild(locacationElement);
        }
        doc.appendChild(root);
        save();
    }

    @SneakyThrows
    private void save() {
        if (doc != null) {
            writeDocument(doc, filename);
        }
    }

    @SneakyThrows
    private void writeDocument(Document document, String path)
            throws TransformerFactoryConfigurationError
    {
        Transformer trf = null;
        DOMSource src = null;
        FileOutputStream fos = null;
        trf = TransformerFactory.newInstance().newTransformer();
        src = new DOMSource(document);
        fos = new FileOutputStream(path);

        StreamResult result = new StreamResult(fos);
        trf.transform(src, result);
    }
}
