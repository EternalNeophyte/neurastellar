package edu.psuti.alexandrov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.psuti.alexandrov.AppPresets.*;

public class NeurastellarApp extends Application {

    public static void main(String... args) {
        loadLogger();
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Neurastellar");
        stage.setScene(new Scene(fxml(), 1200, 750));
        stage.show();
    }
}
