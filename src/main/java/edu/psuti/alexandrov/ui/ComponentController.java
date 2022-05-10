package edu.psuti.alexandrov.ui;

import edu.psuti.alexandrov.AppPresets;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.psuti.alexandrov.AppPresets.*;
import static edu.psuti.alexandrov.ui.Fonts.*;

public class ComponentController implements Initializable {

    @FXML
    private ImageView bgSky;

    @FXML
    private Text appName;

    @FXML
    private Text preName;

    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appName.setFont(font(EXO_2_BOLD, 75));
        preName.setFont(font(EXO_2_BOLD, 28));
        tabPane.getStylesheets().add(css());

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
