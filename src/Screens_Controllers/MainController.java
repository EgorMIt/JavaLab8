package Screens_Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<?, ?> id_city;

    @FXML
    private TableColumn<?, ?> n_city;

    @FXML
    private TableColumn<?, ?> owner_city;

    @FXML
    private TableColumn<?, ?> x_city;

    @FXML
    private TableColumn<?, ?> y_city;

    @FXML
    private TableColumn<?, ?> creation_city;

    @FXML
    private TableColumn<?, ?> area_city;

    @FXML
    private TableColumn<?, ?> population_city;

    @FXML
    private TableColumn<?, ?> meters_city;

    @FXML
    private TableColumn<?, ?> climate_city;

    @FXML
    private TableColumn<?, ?> government_city;

    @FXML
    private TableColumn<?, ?> standart_city;

    @FXML
    private TableColumn<?, ?> birthday_city;

    @FXML
    private TableColumn<?, ?> age_city;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private Button Add;

    @FXML
    private Button Clear;

    @FXML
    void initialize() {
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");



    }
}
