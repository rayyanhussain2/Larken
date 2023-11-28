package duoforge.larken;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class Scene1 implements Initializable {
    //FXML elements
    @FXML
    AnchorPane root;
    @FXML
    Rectangle platform1 = new Rectangle();
    @FXML
    Rectangle platform2 = new Rectangle();
    @FXML
    Rectangle platform3 = new Rectangle();
    @FXML
    Rectangle platform4 = new Rectangle();
    //Classes
    GamePlatform pillars;

    @FXML
    public void shiftAll() throws InterruptedException {
        pillars.shiftPlatforms();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        pillars = new GamePlatform(platform1, platform2, platform3, platform4);
        pillars.setPlatformsInitial();
    }
}