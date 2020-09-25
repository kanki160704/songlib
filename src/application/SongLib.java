package application;

/**
 *
 * @author : Chenqi Zhao, Ruiqi Zhang
 * @date : ${2020.9.24}
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.ListController;

import static javafx.application.Application.launch;

public class SongLib extends Application {
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/ListView.fxml"));
        AnchorPane root = (AnchorPane)loader.load();


        ListController listController = loader.getController();
        listController.start(primaryStage);
        Scene scene = new Scene(root, 800, 500);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
