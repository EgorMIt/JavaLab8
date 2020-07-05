package ClientFullPack.Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import static ClientFullPack.RunClient.login;

public class UpdateController {

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
    private ComboBox<String> climate;

    @FXML
    private ComboBox<String> government;

    @FXML
    private ComboBox<String> st_city;

    @FXML
    private Button update;

    @FXML
    private TextField governorBirthday;

    @FXML
    private Button delete;

    @FXML
    void initialize() {
        ObservableList<String> _climates = FXCollections.observableArrayList(
                Climate.TROPICAL_SAVANNA.getRussianName(),
                Climate.MEDITERRANEAN.getRussianName(),
                Climate.POLAR_ICECAP.getRussianName());

        climate.setItems(_climates);
        climate.setValue(RunClient.cityTable.getClimate().getRussianName());


        ObservableList<String> _grover = FXCollections.observableArrayList(
                Government.KRITARCHY.getRussianName(),
                Government.OLIGARCHY.getRussianName(),
                Government.TIMOCRACY.getRussianName(),
                Government.TOTALITARIANISM.getRussianName());

        government.setItems(_grover);
        government.setValue(RunClient.cityTable.getGovernment().getRussianName());

        ObservableList<String> _standardOfLiving = FXCollections.observableArrayList(
                StandardOfLiving.ULTRA_HIGH.getRussianName(),
                StandardOfLiving.MEDIUM.getRussianName(),
                StandardOfLiving.VERY_LOW.getRussianName(),
                StandardOfLiving.NIGHTMARE.getRussianName());

        st_city.setItems(_standardOfLiving);
        st_city.setValue(RunClient.cityTable.getStandardOfLiving().getRussianName());

        name_city.setText(RunClient.cityTable.getName());
        x_city.setText(String.valueOf(RunClient.cityTable.getX()));
        y_city.setText(String.valueOf(RunClient.cityTable.getY()));
        s_city.setText(String.valueOf(RunClient.cityTable.getArea()));
        p_city.setText(String.valueOf(RunClient.cityTable.getPopulation()));
        grovernorAge.setText(String.valueOf(RunClient.cityTable.getAge()));
        m_city.setText(String.valueOf(RunClient.cityTable.getMetersAboveSeaLevel()));
        governorBirthday.setText(String.valueOf(RunClient.cityTable.getDateOfBirthday()));

        delete.setOnAction(event->{
            try {
                Network network = new Network(RunClient.ip_adress, RunClient.port);
                Message message = new Message(Commands.REMOVE.getCommandName(), RunClient.cityTable.getId().toString(),
                        new User(RunClient.login, RunClient.pass));
                network.write(message);
                delete.getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        update.setOnAction(e->{
            City city = new City(name_city.getText(),
                    new Coordinates(Integer.parseInt(x_city.getText()),
                            Float.parseFloat(y_city.getText())),
                    Long.parseLong(s_city.getText()),
                    Integer.parseInt(p_city.getText()),
                    Long.parseLong(m_city.getText()),
                    Climate.StringNameToClimateObj(climate.getValue()),
                    Government.StringNameToGovernmentObj(government.getValue()),
                    StandardOfLiving.StringNameToStandardOfLivingObj(st_city.getValue()),
                    new Human(Integer.parseInt(grovernorAge.getText()),
                            RunClient.cityTable.getDateOfBirthday()));
            city.setId(RunClient.cityTable.getId());
            try {
                Network network = new Network(RunClient.ip_adress, RunClient.port);
                Message message = new Message(Commands.REMOVE.getCommandName(), RunClient.cityTable.getId().toString(),
                        new User(RunClient.login, RunClient.pass));
                network.write(message);
                network = new Network(RunClient.ip_adress, RunClient.port);
                message = new Message(Commands.ADD.getCommandName(), city,
                        new User(RunClient.login, RunClient.pass));
                network.write(message);
                update.getScene().getWindow().hide();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }
}
