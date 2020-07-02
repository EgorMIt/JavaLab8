package ClientFullPack.Screens_Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

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
    private DatePicker grovernorBirthday;

    @FXML
    private ComboBox<?> climate;

    @FXML
    private ComboBox<?> grovernment;

    @FXML
    private ComboBox<?> st_city;

    @FXML
    private Button add;

    @FXML
    void initialize() {

    }
}