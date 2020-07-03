package ClientFullPack.Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ClientFullPack.RunClient;
import ClientFullPack.connection.Network;
import ClientFullPack.request.Commands;
import app.collection.City;
import io.Message;
import io.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static ClientFullPack.RunClient.login;

public class App_main_Controller {

    public ObservableList<City> cities = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TableView<City> objectTable;

    @FXML
    private TableColumn<City, Long> ID;

    @FXML
    private TableColumn<City, String> Owner;

    @FXML
    private TableColumn<City, String> Name;

    @FXML
    private TableColumn<City, Integer> X;

    @FXML
    private TableColumn<City, Double> Y;

    @FXML
    private TableColumn<City, LocalDateTime> CreationDate;

    @FXML
    private TableColumn<City, Integer> Area;

    @FXML
    private TableColumn<City, Integer> Population;

    @FXML
    private TableColumn<City, Integer> MetersAboveSeaLevel;

    @FXML
    private TableColumn<City, String> Climate;

    @FXML
    private TableColumn<City, String> Government;

    @FXML
    private TableColumn<City, String> StandartOfLiving;

    @FXML
    private TableColumn<City, Integer> Age;

    @FXML
    private TableColumn<City, LocalDateTime> Birthday;

    @FXML
    private Button Add_button;

    @FXML
    private Button Clear_button;

    @FXML
    private Button Remove;

    @FXML
    private Button Remove_if_greater;

    @FXML
    private Button Remove_if_lower;

    @FXML
    private Button Update_id;

    @FXML
    private Button Avarage_meters;

    @FXML
    private Button Replace;

    @FXML
    void initialize() {
        ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);

        Add_button.setOnAction(event->{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("addScene.fxml"));

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
        Clear_button.setOnAction(e->{
            getClientObjects();
        });
    }

    private void getClientObjects(){
        try {
            Network network = new Network(RunClient.ip_adress, RunClient.port);

            User user = new User(login,RunClient.pass);

            Message message = new Message(Commands.SHOW.getCommandName(),user);


            network.write(message);

            ArrayList<City> arrayList  = (ArrayList) network.read();

            cities.addAll(arrayList);

            ID.setCellValueFactory(new PropertyValueFactory<City, Long>("Id"));
            Owner.setCellValueFactory(new PropertyValueFactory<City, String>("Owner"));
            Name.setCellValueFactory(new PropertyValueFactory<City, String>("Name"));
            X.setCellValueFactory(new PropertyValueFactory<City, Integer>("X"));
            Y.setCellValueFactory(new PropertyValueFactory<City, Double>("Y"));
            CreationDate.setCellValueFactory(new PropertyValueFactory<City, LocalDateTime>("CreationDate"));
            Area.setCellValueFactory(new PropertyValueFactory<City, Integer>("Area"));
            Population.setCellValueFactory(new PropertyValueFactory<City, Integer>("Population"));
            MetersAboveSeaLevel.setCellValueFactory(new PropertyValueFactory<City, Integer>("MetersAboveSeaLevel"));
            Climate.setCellValueFactory(new PropertyValueFactory<City, String>("Climate"));
            Government.setCellValueFactory(new PropertyValueFactory<City, String>("Government"));
            StandartOfLiving.setCellValueFactory(new PropertyValueFactory<City, String>("StandartOfLiving"));
            Age.setCellValueFactory(new PropertyValueFactory<City, Integer>("Age"));
            Birthday.setCellValueFactory(new PropertyValueFactory<City, LocalDateTime>("Birthday"));

            objectTable.setItems(cities);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
