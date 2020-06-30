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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");

       Input.setOnAction(event->{
           if(IP_adress.getText().trim().equals("1") && Port.getText().trim().equals("1")){
               Input.getScene().getWindow().hide();
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("authScene.fxml"));

               try {
                   loader.load();
               } catch (IOException e) {
                   e.printStackTrace();
               }

               Parent root = loader.getRoot();
               Stage stage = new Stage();
               stage.setScene(new Scene(root));
               stage.showAndWait();
           }else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Error");
               alert.setHeaderText("Ошибка ввода Адреса/Порта");
               alert.setContentText("Можно вводить только числа!");
               alert.showAndWait().ifPresent(rs -> {
               });
           }
       });
    }
}
