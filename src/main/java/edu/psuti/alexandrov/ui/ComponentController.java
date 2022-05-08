package edu.psuti.alexandrov.ui;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ComponentController implements Initializable {

    @FXML
    private ImageView bgSky;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tabPane.setStyle(
                """
                            .tab-pane .tab-header-area .tab-header-background {
                                        -fx-effect: null;
                                    }
                        """
        );
        //tabPane.lookup(".tab-pane .tab-header-area .tab-header-background").setStyle("-fx-effect: null;");

        var rotate = new RotateTransition();
        rotate.setByAngle(360);
        rotate.setDuration(Duration.seconds(60));
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);

        var scale = new ScaleTransition();
        scale.setByX(1.2);
        scale.setByY(1.2);
        scale.setDuration(Duration.seconds(15));
        scale.setCycleCount(RotateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setAutoReverse(true);

        var parrallel = new ParallelTransition(rotate, scale);
        parrallel.setNode(bgSky);
        parrallel.play();
    }
}
