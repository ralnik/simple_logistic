package ru.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import ru.example.db.dao.LoadsDao;
import ru.example.db.dao.LoadsDaoImpl;
import ru.example.db.response.CountCargoesInItemResponse;
import ru.example.xml.CountCargoesXmlParser;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    private LoadsDao locationDao = new LoadsDaoImpl();
    @FXML
    private TableView<CountCargoesInItemResponse> table;
    @FXML
    private TextField editSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Do nothing
    }

    @SuppressWarnings("unchecked")
    private void init(List<CountCargoesInItemResponse> countCargoesInItemResponseList) {
        ObservableList<CountCargoesInItemResponse> countCargoesInItemResponseObservableList =
                FXCollections.observableList(countCargoesInItemResponseList);

        TableColumn<CountCargoesInItemResponse, String> locationNameColumn = new TableColumn<>("location name");
        locationNameColumn.setCellValueFactory(new PropertyValueFactory<>("locationName"));

        TableColumn<CountCargoesInItemResponse, Integer> locationIdColumn = new TableColumn<>("location Id");
        locationIdColumn.setCellValueFactory(new PropertyValueFactory<>("locationId"));

        TableColumn<CountCargoesInItemResponse, String> loadsNameColumn = new TableColumn<>("loads name");
        loadsNameColumn.setCellValueFactory(new PropertyValueFactory<>("loadsName"));

        TableColumn<CountCargoesInItemResponse, Integer> loadsIdColumn = new TableColumn<>("loads Id");
        loadsIdColumn.setCellValueFactory(new PropertyValueFactory<>("loadsId"));

        table.getColumns().clear();
        table.getColumns().setAll(locationNameColumn, locationIdColumn, loadsNameColumn, loadsIdColumn);
        table.setItems(countCargoesInItemResponseObservableList);
    }

    @SneakyThrows
    public void buttonCreateCargoesOnClick() {
        String fxmlFile = "/fxml/CreateNewCargoesForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Create count cargoes");
        stage.show();
    }

    public void buttonSearchOnClick() {
        List<CountCargoesInItemResponse> countCargoesInItemResponseList =
                locationDao.getCountCargoesInItem(editSearch.getText());
        init(countCargoesInItemResponseList);
    }

    public void buttonExportOnClick() {
        CountCargoesXmlParser parser = new CountCargoesXmlParser("cargoes.xml");
        parser.exportXml(locationDao.getCountCargoesToExportXml(editSearch.getText()));
    }
}
