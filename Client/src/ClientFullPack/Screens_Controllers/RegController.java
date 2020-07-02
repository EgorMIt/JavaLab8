package ClientFullPack.Screens_Controllers;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import io.Message;
import io.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);
        Languages.setValue("Русский");

        Reg.setOnAction(event->{
            //Network network = new Network(RunClient.ip_adress, RunClient.port);
            String login = LoginReg.getText().trim();
            String pass = PswrdReg.getText().trim();
            if(!login.isEmpty() && login.length() == login.replaceAll("[^A-Za-z0-9]", "").length()){
                if(!pass.isEmpty() && pass.length() == pass.replaceAll("[^A-Za-z0-9]", "").length()){
                    Network network = null;
                    try {
                        network = new Network(RunClient.ip_adress, RunClient.port);
                        User user = new User(login,pass);

                        Message message = new Message("REGISTRATION",user,true);

                        network.write(message);

                        if(network.read().toString().equals(RunClient.REGISTRATION_ISSUCESS)){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION); //если проверка не прошла
                            alert.setTitle("Успех");
                            alert.setHeaderText("Успешная регистрация");
                            alert.setContentText("Поздравляю вы зарегестрировали в системе");
                            alert.showAndWait().ifPresent(rs -> {
                            });
                            Reg.getScene().getWindow().hide();
                        }else{
                            LoginReg.setText("");
                            PswrdReg.setText("");
                            Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                            alert.setTitle("Error");
                            alert.setHeaderText("Ошибка регистрации");
                            alert.setContentText("Такой логин уже использовался");
                            alert.showAndWait().ifPresent(rs -> {
                            });
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    LoginReg.setText("");
                    PswrdReg.setText("");
                    Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                    alert.setTitle("Error");
                    alert.setHeaderText("Ошибка ввода Адреса/Порта");
                    alert.setContentText("Проверьте правильность ввода пароля!");
                    alert.showAndWait().ifPresent(rs -> {
                    });
                }
            }else{
                LoginReg.setText("");
                PswrdReg.setText("");
                Alert alert = new Alert(Alert.AlertType.ERROR); //если проверка не прошла
                alert.setTitle("Error");
                alert.setHeaderText("Ошибка ввода Адреса/Порта");
                alert.setContentText("Проверьте правильность ввода логина!");
                alert.showAndWait().ifPresent(rs -> {
                });
            }
        });
    }
}
