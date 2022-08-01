package View;

import DataBase.DataBase;
import component.User;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {
    public static User user=new User();
    public static Stage stage=new Stage();
    public static Scene startPage;

    public static void showHomePage(String id) throws IOException {
        Controller.user= DataBase.getUserWithId(id);

        Parent pane = FXMLLoader.load(Controller.class.getResource("/fxml/HomePage.fxml"));
        Scene scene = new Scene(pane);
        Controller.stage.setScene(scene);
    }


    public static void changeTextFieldColor(TextField textField, String promptText, String color, Boolean timer,Boolean clicked){
        textField.setStyle("-fx-background-color:"+color);
        textField.setText("");
        textField.setPromptText(promptText);

        if (clicked) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    textField.setStyle("-fx-background-color: #efece6");
                    textField.setStyle("-fx-background-radius: 50");
                }
            });
        }

        if (timer) {
            Timer myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    textField.setStyle("-fx-background-color: #efece6");
                    textField.setStyle("-fx-background-radius: 50");
                }
            }, 3000l);
        }
    }



}
