package ru.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.example.db.dao.LoadsDao;
import ru.example.db.dao.LoadsDaoImpl;
import ru.example.db.dao.LocationDao;
import ru.example.db.dao.LocationDaoImpl;
import ru.example.db.entities.Loads;
import ru.example.db.entities.Location;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateNewCargoesController implements Initializable {
    private LocationDao locationDao = new LocationDaoImpl();
    private LoadsDao loadsDao = new LoadsDaoImpl();

    @FXML
    private Spinner<Integer> editCountCargoes;
    @FXML
    private TextField editName;
    @FXML
    private Button buttonCancel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Do nothing
    }

    public void buttonCreateOnClick() {
        Location location = new Location();
        location.setName(editName.getText());
        location = locationDao.save(location);
        for (int item = 1; item <= editCountCargoes.getValue(); item++) {
            Loads loads = new Loads();
            loads.setName("AAAA." + location.getId() + "." + item);
            loads.setLocation(location);
            loadsDao.save(loads);
        }
        buttonCancelOnClick();
    }

    public void buttonCancelOnClick() {
        Stage stage = (Stage) buttonCancel.getScene().getWindow();
        stage.close();
    }
}
