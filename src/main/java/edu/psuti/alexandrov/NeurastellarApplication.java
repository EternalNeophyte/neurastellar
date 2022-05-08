package edu.psuti.alexandrov;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class NeurastellarApplication extends Application {

    public static void main(String... args) {
        LoggerSource.load();
        launch(args);
        //https://docs.djl.ai/docs/development/how_to_use_dataset.html

        //https://docs.djl.ai/jupyter/tutorial/01_create_your_first_network.html

        //!! https://towardsdatascience.com/deep-learning-in-java-d9b54ae1423a
    }

    @Override
    public void start(Stage stage) throws IOException {
        Parent src = FXMLLoader.load(NeurastellarApplication.class.getResource("/ui.fxml"));
        stage.setTitle("Neurastellar");
        stage.setScene(new Scene(src, 750, 500));
        stage.show();
    }
}
