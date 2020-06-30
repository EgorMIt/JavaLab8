package Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TextField LoginReg;

    @FXML
    private TextField PswrdReg;

    @FXML
    private Button Reg;

    @FXML
    void initialize() {
        ObservableList<String> langs= FXCollections.observableArrayList("Русский", "Белорусский", "Венгерский", "Испанский");

        Languages.setItems(langs);
        Languages.setValue("Русский");

        Reg.setOnAction(event->{
            Reg.getScene().getWindow().hide();
        });
    }
}
