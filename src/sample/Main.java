package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    private Scene scene;
    private AnchorPane pane;



    @FXML
    private Button btn;



    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Test");
        primaryStage.setScene(new Scene(root,1000,500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
