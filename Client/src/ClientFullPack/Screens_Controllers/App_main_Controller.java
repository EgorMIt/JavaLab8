package ClientFullPack.Screens_Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.TimerTask;

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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static ClientFullPack.RunClient.login;

public class App_main_Controller {

    public ObservableList<CityTable> cities = FXCollections.observableArrayList();

    private static int collectionSize;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> Languages;

    @FXML
    private TableView<CityTable> objectTable;

    @FXML
    private TableColumn<CityTable, Long> ID;

    @FXML
    private TableColumn<CityTable, String> Owner;

    @FXML
    private TableColumn<CityTable, String> Name;

    @FXML
    private TableColumn<CityTable, Integer> X;

    @FXML
    private TableColumn<CityTable, Double> Y;

    @FXML
    private TableColumn<CityTable, LocalDateTime> CreationDate;

    @FXML
    private TableColumn<CityTable, Integer> Area;

    @FXML
    private TableColumn<CityTable, Integer> Population;

    @FXML
    private TableColumn<CityTable, Integer> MetersAboveSeaLevel;

    @FXML
    private TableColumn<CityTable, String> Climate;

    @FXML
    private TableColumn<CityTable, String> Government;

    @FXML
    private TableColumn<CityTable, String> StandartOfLiving;

    @FXML
    private TableColumn<CityTable, Integer> Age;

    @FXML
    private TableColumn<CityTable, LocalDateTime> Birthday;

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

    void update_table(){

    }

    @FXML
    void initialize() {

        TimerTask task = new TimerTask() {
            public void run() {
                try {
                    Network network =  network = new Network(RunClient.ip_adress, RunClient.port);
                    User user = new User(login,RunClient.pass);
                    Message message = new Message(Commands.SHOW.getCommandName(),user);
                    network.write(message);
                    ArrayList<City> arrayList  = (ArrayList) network.read();
                    if(arrayList.size() > collectionSize) {
                        collectionSize = arrayList.size();
                        getClientObjects(arrayList);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        };

        RunClient.timer.schedule(task, 0L, 1000L);


    ObservableList<String> languages= FXCollections.observableArrayList("Русский", "Беларускі", "Magyar", "Español");

        Languages.setItems(languages);


        objectTable.setOnMouseClicked(event -> { //Двойной клик по объекту в таблице
            if (event.getClickCount() == 2) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Auth error");
                    alert.setHeaderText("Auth error");
                    alert.setContentText("Access error");
                    alert.showAndWait().ifPresent(rs -> {});
            }
        });


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
    }

    private void getClientObjects(ArrayList<City> arrayList){
        cities.clear();

        for(City el: arrayList){
            cities.add(new CityTable(el.getId(),el.getOwner(),el.getName(),el.getCoordinates().getX(),
                    el.getCoordinates().getY(),el.getCreationDate(),el.getArea(),el.getPopulation(),
                    el.getMetersAboveSeaLevel(),el.getClimate(),el.getGovernment(),el.getStandardOfLiving(),
                    el.getGovernor().getAge(),el.getGovernor().getDateOfBirthday()));
        }


        ID.setCellValueFactory(new PropertyValueFactory<CityTable, Long>("Id"));
        Owner.setCellValueFactory(new PropertyValueFactory<CityTable, String>("Owner"));
        Name.setCellValueFactory(new PropertyValueFactory<CityTable, String>("Name"));
        X.setCellValueFactory(new PropertyValueFactory<CityTable, Integer>("X"));
        Y.setCellValueFactory(new PropertyValueFactory<CityTable, Double>("Y"));
        CreationDate.setCellValueFactory(new PropertyValueFactory<CityTable, LocalDateTime>("creationDate"));
        Area.setCellValueFactory(new PropertyValueFactory<CityTable, Integer>("Area"));
        Population.setCellValueFactory(new PropertyValueFactory<CityTable, Integer>("Population"));
        MetersAboveSeaLevel.setCellValueFactory(new PropertyValueFactory<CityTable, Integer>("MetersAboveSeaLevel"));
        Climate.setCellValueFactory(new PropertyValueFactory<CityTable, String>("Climate"));
        Government.setCellValueFactory(new PropertyValueFactory<CityTable, String>("Government"));
        StandartOfLiving.setCellValueFactory(new PropertyValueFactory<CityTable, String>("standardOfLiving"));
        Age.setCellValueFactory(new PropertyValueFactory<CityTable, Integer>("Age"));
        Birthday.setCellValueFactory(new PropertyValueFactory<CityTable, LocalDateTime>("dateOfBirthday"));

        objectTable.setItems(cities);
    }

}
