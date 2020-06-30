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

public class AuthController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TextField LoginAuth;

    @FXML
    private TextField PswrdAuth;

    @FXML
    private Button Auth;

    @FXML
    private Button Register;

    @FXML
    void initialize() {
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");

        Register.setOnAction(event->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("registerScene.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        Auth.setOnAction(event->{
            if(LoginAuth.getText().trim().equals("1") && PswrdAuth.getText().trim().equals("1")){
                Auth.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("mainScene.fxml"));
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
                alert.setHeaderText("Пошёл нахуЙ");
                alert.setContentText("По причине долбаёб");
                alert.showAndWait().ifPresent(rs -> {});
            }
        });
    }
}
