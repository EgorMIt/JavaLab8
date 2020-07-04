package ClientFullPack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;


public class RunClient extends Application {

    public static String ip_adress;
    public static int port;
    public static String login;
    public static String pass;
    public static String owner;
    public static final String REGISTRATION_ISSUCESS = "[110100011000000011010000101101011101000010110011]";
    public static final String AUTHORIZATION_ISSUCCESS = "[110100001011000011010000101100101101000110000010]";
    public static Timer timer  = new Timer();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Screens_Controllers/addressScene.fxml"));
        primaryStage.setTitle("Вводим порт");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
