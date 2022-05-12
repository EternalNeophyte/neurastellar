package edu.psuti.alexandrov;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static edu.psuti.alexandrov.AppPresets.*;

public class NeurastellarApp extends Application {

    public static void main(String... args) {
        loadLogger();
        launch(args);
        //https://docs.djl.ai/docs/development/how_to_use_dataset.html
        //https://docs.djl.ai/jupyter/tutorial/01_create_your_first_network.html
        //!! https://towardsdatascience.com/deep-learning-in-java-d9b54ae1423a

        //https://towardsdatascience.com/deep-java-library-djl-a-deep-learning-toolkit-for-java-developers-55d5a45bca7e
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Neurastellar");
        stage.setScene(new Scene(fxml(), 1200, 750));
        stage.show();
    }
}
