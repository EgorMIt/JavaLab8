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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private static int port;
    private static String ip_adress;

    @FXML
    void initialize() {
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");

        Input.setOnAction(event->{
            port = Integer.parseInt(Port.getText());
            ip_adress = IP_adress.getText().toString();

            ClientManager clientManager = new ClientManager();
            clientManager.begin();
           if(port > 1023 && port < 65535 && !Port.getText().isEmpty()){ //здесь должна быть провекра на правильность формата ввода
               Input.getScene().getWindow().hide();
               FXMLLoader loader = new FXMLLoader();
               loader.setLocation(getClass().getResource("authScene.fxml")); //загрузка экрана входа

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
               Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
               alert.setTitle("Error");
               alert.setHeaderText("Ошибка ввода Адреса/Порта");
               alert.setContentText("Проверьте правильность ввода!");
               alert.showAndWait().ifPresent(rs -> {
               });
           }
       });
    }
    public static int getPort(){
        return port;
    }
    public static String getIpadress(){
        return ip_adress;
    }
}
