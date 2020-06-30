package Screens_Controllers.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AddressController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TextField IP_adress;

    @FXML
    private TextField Port;

    @FXML
    private Button Input;

    @FXML
    void initialize() {

        ObservableList<String> langs= FXCollections.observableArrayList("Русский", "Белорусский", "Венгерский", "Испанский");

        Languages.setItems(langs);
        Languages.setValue("Русский");

       Input.setOnAction(event->{
           System.out.println(Port.getText());
       });
    }
}
