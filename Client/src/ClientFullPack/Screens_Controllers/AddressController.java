package ClientFullPack.Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import ClientFullPack.RunClient;
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
            String addressConnect = IP_adress.getText().trim();
            String portConnect = Port.getText().trim();
            if(addressConnect.length() == addressConnect.replaceAll("[^0-9.]","").length() || addressConnect.toLowerCase().equals("localhost")) {//здесь должна быть провекра на правильность формата ввода
                if (portConnect.length() == portConnect.replaceAll("[^0-9]", "").length()) {
                    //try {
                        if(!addressConnect.equals("localhost"))
                            addressConnect = addressConnect.replaceAll("[^0-9.]","");
                        //Network network = new Network(addressConnect.toLowerCase(),Integer.parseInt(portConnect));

                        RunClient.ip_adress = addressConnect;
                        RunClient.port = Integer.parseInt(portConnect);

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
                   /* } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                        alert.setTitle("Error");
                        alert.setHeaderText("Ошибка подключения");
                        alert.setContentText("Ошибка подключения!!!\nМожно пойти перекурить)");
                        alert.showAndWait().ifPresent(rs -> {
                        });
                    }*/
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                    alert.setTitle("Error");
                    alert.setHeaderText("Ошибка ввода Адреса/Порта");
                    alert.setContentText("Проверьте правильность ввода!");
                    alert.showAndWait().ifPresent(rs -> {
                    });
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                alert.setTitle("Error");
                alert.setHeaderText("Ошибка ввода Адреса/Порта");
                alert.setContentText("Проверьте правильность ввода!");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
       });
    }
}
