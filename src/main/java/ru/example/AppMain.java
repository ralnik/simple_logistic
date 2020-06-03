package ru.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.example.configuration.Config;
import ru.example.db.dao.LoadsDao;
import ru.example.db.dao.LoadsDaoImpl;
import ru.example.db.dao.LocationDao;
import ru.example.db.dao.LocationDaoImpl;

import java.io.File;

/**
 * Hello world!
 */
public class AppMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        createTable();

        String fxmlFile = "/fxml/MainForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setScene(new Scene(root));
        stage.setTitle("Simple logistic");
        stage.show();
    }

    private void createTable() {
        File file = new File(Config.getProperty("sqlite.name"));
        if (!file.exists()) {
            LocationDao locationDao = new LocationDaoImpl();
            locationDao.createTable();

            LoadsDao loadsDao = new LoadsDaoImpl();
            loadsDao.createTable();
            loadsDao.createViews();
        }
    }
}
