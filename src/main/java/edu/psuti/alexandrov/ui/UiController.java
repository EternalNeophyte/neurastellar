package edu.psuti.alexandrov.ui;

import edu.psuti.alexandrov.task.TrainModelTask;
import edu.psuti.alexandrov.util.FlexibleExecutor;
import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

import static edu.psuti.alexandrov.AppPresets.*;
import static edu.psuti.alexandrov.ui.Fonts.*;

public class UiController implements Initializable {

    @FXML
    private ImageView bgSky;

    @FXML
    private Text appName;

    @FXML
    private Text preName;

    @FXML
    private TabPane tabPane;

    @FXML
    private ImageView resumeTrain;

    @FXML
    private ImageView pauseTrain;

    @FXML
    private Label trainStatus;

    @FXML
    private ProgressBar trainProgress;

    @FXML
    private TextArea trainLogs;

    private final FlexibleExecutor taskExecutor = new FlexibleExecutor();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        appName.setFont(font(EXO_2_LIGHT, 75));
        preName.setFont(font(EXO_2_LIGHT, 25));
        trainStatus.setFont(font(EXO_2_LIGHT, 16));
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

    public void onTrainButtonClick(Event e) {
        boolean resumeButtonIsHiding = resumeTrain.getOpacity() > 0;
        resumeTrain.setOpacity(resumeButtonIsHiding ? 0 : 1);
        pauseTrain.setOpacity(resumeButtonIsHiding ? 1 : 0);
        if(taskExecutor.isFree()) {
            TrainModelTask task = new TrainModelTask();
            trainStatus.textProperty().unbind();
            trainStatus.textProperty().bind(task.messageProperty());
            trainProgress.progressProperty().unbind();
            trainProgress.progressProperty().bind(task.progressProperty());
            taskExecutor.submit(task);
        }
        else if(taskExecutor.isPaused()) {
            taskExecutor.resume();
        }
        else {
            taskExecutor.pause();
        }
    }
}
