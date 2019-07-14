package edu.gwu.csci6010;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final int APP_WIDTH = 1600, APP_HEIGHT = 1200;
    private static final String TITLE = "8 Queens";

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/8queens.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle(TITLE);
        Scene scene = new Scene(root, APP_WIDTH, APP_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/gui/8queens.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
