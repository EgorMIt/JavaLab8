package ClientFullPack.Screens_Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class App_main_Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<?> Languages;

    @FXML
    private TableView<?> objectTable;

    @FXML
    private TableColumn<?, ?> ID;

    @FXML
    private TableColumn<?, ?> Owner;

    @FXML
    private TableColumn<?, ?> Name;

    @FXML
    private TableColumn<?, ?> X;

    @FXML
    private TableColumn<?, ?> Y;

    @FXML
    private TableColumn<?, ?> CreationDate;

    @FXML
    private TableColumn<?, ?> Area;

    @FXML
    private TableColumn<?, ?> Population;

    @FXML
    private TableColumn<?, ?> MetersAboveSeaLevel;

    @FXML
    private TableColumn<?, ?> Climate;

    @FXML
    private TableColumn<?, ?> Government;

    @FXML
    private TableColumn<?, ?> StandartOfLiving;

    @FXML
    private TableColumn<?, ?> Governor;

    @FXML
    private TableColumn<?, ?> Age;

    @FXML
    private TableColumn<?, ?> Birthday;

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


    }
}
