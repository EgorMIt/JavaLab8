package ClientFullPack.Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import ClientFullPack.request.Commands;
import app.collection.*;
import io.Message;
import io.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.web.HTMLEditorSkin;

public class AddController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name_city;

    @FXML
    private TextField x_city;

    @FXML
    private TextField y_city;

    @FXML
    private TextField s_city;

    @FXML
    private TextField p_city;

    @FXML
    private TextField grovernorAge;

    @FXML
    private TextField m_city;

    @FXML
    private TextField grovernorBirthday;

    @FXML
    private ComboBox<String> climate;

    @FXML
    private ComboBox<String> grovernment;

    @FXML
    private ComboBox<String> st_city;

    @FXML
    private Button add;

    @FXML
    void initialize() {
        ObservableList<String> Climates= FXCollections.observableArrayList("TROPICAL_SAVANNA",
                "MEDITERRANEAN", "POLAR_ICECAP");

        climate.setItems(Climates);
        climate.setValue("TROPICAL_SAVANNA");

        ObservableList<String> Grover = FXCollections.observableArrayList("KRITARCHY",
                "OLIGARCHY", "TIMOCRACY", "TOTALITARIANISM");

        grovernment.setItems(Grover);
        grovernment.setValue("KRITARCHY");

        ObservableList<String> st = FXCollections.observableArrayList("ULTRA_HIGH",
                "MEDIUM", "VERY_LOW", "NIGHTMARE");

        st_city.setItems(st);
        st_city.setValue("ULTRA_HIGH");


        add.setOnAction(event->{
            LocalDateTime dateOfBirthday;
            String line = grovernorBirthday.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateOfBirthday = LocalDateTime.parse(line, formatter);

            Human governor = new Human(Integer.parseInt(grovernorAge.getText()), dateOfBirthday);

            Coordinates coordinates = new Coordinates(Integer.parseInt(x_city.getText()),Float.parseFloat(y_city.getText()));
            City city = new City(name_city.getText(),coordinates,Long.parseLong(s_city.getText()),
                    Integer.parseInt(p_city.getText()),Long.parseLong(m_city.getText()),Climate.valueOf(climate.getValue())
                    ,Government.valueOf(grovernment.getValue()),StandardOfLiving.valueOf(st_city.getValue()),governor);

            try {
                Network network = new Network(RunClient.ip_adress,RunClient.port);

                User user = new User(RunClient.login,RunClient.pass);

                Message message = new Message(Commands.ADD.getCommandName(),city,user);

                network.write(message);

                Alert alert = new Alert(Alert.AlertType.INFORMATION); //если проверка не прошла
                alert.setTitle("Успех");
                alert.setHeaderText("Успешное добавление");
                alert.setContentText("Поздравляю вы успешно добавили город");
                alert.showAndWait().ifPresent(rs -> {
                });

                add.getScene().getWindow().hide();

            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
}