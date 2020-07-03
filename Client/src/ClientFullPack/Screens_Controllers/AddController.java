package ClientFullPack.Screens_Controllers;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import ClientFullPack.request.Commands;
import app.collection.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

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
    private TextField governorBirthday;

    @FXML
    private ComboBox<String> climate;

    @FXML
    private ComboBox<String> government;

    @FXML
    private ComboBox<String> st_city;

    @FXML
    private Button add;

    @FXML
    void initialize() {
        ObservableList<String> _climates = FXCollections.observableArrayList(
                Climate.TROPICAL_SAVANNA.getRussianName(),
                Climate.MEDITERRANEAN.getRussianName(),
                Climate.POLAR_ICECAP.getRussianName());

        climate.setItems(_climates);
        //climate.setValue(Climate.POLAR_ICECAP.getRussianName());


        ObservableList<String> _grover = FXCollections.observableArrayList(
                Government.KRITARCHY.getRussianName(),
                Government.OLIGARCHY.getRussianName(),
                Government.TIMOCRACY.getRussianName(),
                Government.TOTALITARIANISM.getRussianName());

        government.setItems(_grover);
        //government.setValue(Government.TOTALITARIANISM.getRussianName());

        ObservableList<String> _standardOfLiving = FXCollections.observableArrayList(
                StandardOfLiving.ULTRA_HIGH.getRussianName(),
                StandardOfLiving.MEDIUM.getRussianName(),
                StandardOfLiving.VERY_LOW.getRussianName(),
                StandardOfLiving.NIGHTMARE.getRussianName());

        st_city.setItems(_standardOfLiving);
        //st_city.setValue(StandardOfLiving.NIGHTMARE.getRussianName());

        add.setOnAction(event -> {
            try {


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalDateTime _governorBirthday = LocalDateTime.parse(governorBirthday.getText().trim(), formatter);

                City city = new City(
                        name_city.getText(),
                        new Coordinates(Integer.parseInt(x_city.getText()),
                        Float.parseFloat(y_city.getText())),
                        Long.parseLong(s_city.getText()),
                        Integer.parseInt(p_city.getText()),
                        Long.parseLong(m_city.getText()),
                        Climate.StringNameToClimateObj(climate.getValue()),
                        Government.StringNameToGovernmentObj(government.getValue()),
                        StandardOfLiving.StringNameToStandardOfLivingObj(st_city.getValue()),
                        new Human(Integer.parseInt(grovernorAge.getText()),
                                _governorBirthday));

                try {
                    Network network = new Network(RunClient.ip_adress, RunClient.port);
                    Message message = new Message(Commands.ADD.getCommandName(), city,
                            new User(RunClient.login, RunClient.pass));
                    network.write(message);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION); //если проверка не прошла
                    alert.setTitle("Чпуньк");
                    alert.setHeaderText("Успешное добавление!");
                    alert.setContentText("Поздравляю вы успешно добавили город " + city.getName());
                    alert.showAndWait().ifPresent(rs -> {
                    });

                    add.getScene().getWindow().hide();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (NumberFormatException | DateTimeParseException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Поле имеет неверный формат");
                alert.setContentText("Ошибка при заполнении поля");
                alert.showAndWait().ifPresent(rs -> {
                });
            }

        });
    }
}