package duoforge.larken;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
    ImageView character = new ImageView();
    @FXML
    Rectangle stickAsset = new Rectangle();

    @FXML
    Button reset = new Button();

    @FXML
    Button revive = new Button();

    @FXML
    Text currentScore = new Text();

    @FXML
    Text collectiblesTotal = new Text();

    @FXML
    ImageView BG1L1 = new ImageView();

    @FXML
    ImageView BG1L2 = new ImageView();

    @FXML
    ImageView BG1L3 = new ImageView();

    @FXML
    ImageView BG2L1 = new ImageView();

    @FXML
    ImageView BG2L2 = new ImageView();

    @FXML
    ImageView BG2L3 = new ImageView();

    @FXML
    Pane pauseMainPane = new Pane();

    @FXML
    ImageView collectibleImage = new ImageView();

    //Classes
    GamePlatform pillars;
    PlayableCharacter mainCharacter;
    Timeline universalAnim1;
    Timeline universalAnim2;

    Integer score;
    Integer cherries;
    Integer currentScoreCount;

    Stick stick;
    private Boolean isClicked;

    Background L1;

    Background L2;

    Background L3;
    PauseScreen pauseScreen;

    Collectible collectible;
    AnimationTimer universalAnimationTimer;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pillars = GamePlatform.returnInstance(platform1, platform2, platform3); // Singleton
        assert pillars != null;
        pillars.setPlatformsInitial();

        mainCharacter = PlayableCharacter.returnInstance(character, pillars.platforms.get(0).getWidth() - 5); // singleton
        stick = Stick.getInstance(stickAsset, pillars.platforms.get(0).getWidth());
        universalAnim1 = new Timeline();
        currentScoreCount = 0;
        isClicked = false;

        L1 = new Background(BG1L1, BG2L1, 0);
        L2 = new Background(BG1L2, BG2L2, 1);
        L3 = new Background(BG1L3, BG2L3, 2);
        L1.setOffset(20);
        L2.setOffset(25);
        L3.setOffset(60);

        Random rand = new Random();
        Integer firstRand = rand.nextInt(0, 5);
        Integer secondRand = rand.nextInt(0, 5);

        L1.setBG(firstRand, secondRand);
        L2.setBG(firstRand, secondRand);
        L3.setBG(firstRand, secondRand);

        pauseScreen = new PauseScreen(pauseMainPane, reset, revive);

        collectible = new Collectible(collectibleImage);
        cherries = 0;
    }

    @FXML
    public void onGameInput() throws InterruptedException { //facade
        //Platform.runLater(pillars);
        //mainCharacter.goNextPillar(pillars.getPlatform2().getX() + pillars.getPlatform2().getWidth() - mainCharacter.getWidth());
        //mainCharacter.fall();
        if(!isClicked) {
            stick.elongateStick();
            isClicked = true;
            collectible.set(pillars.platforms.get(0).getWidth(), pillars.platforms.get(1).getX());
        }
    }


    @FXML
    public void onGameInputRelease() throws InterruptedException {
        if(isClicked){
            universalAnim1 = new Timeline();
            Integer outputVal = stick.pauseStick(pillars.platforms.get(0).getX(), pillars.platforms.get(0).getWidth(), pillars.platforms.get(1).getX(), pillars.platforms.get(1).getWidth());
            System.out.println(outputVal);
            if (outputVal == 1) {
                //Handled collision for case 1
                universalAnimationTimer = new AnimationTimer() {
                    @Override
                    public void handle(long l) {

                    }
                };
                //collectible
                //shift character animation
                mainCharacter.changeGIF();
                mainCharacter.goNextPillar(collectible, universalAnim1, pillars.getPlatform2().getX() + pillars.getPlatform2().getWidth() - mainCharacter.getWidth(), pillars.platforms.get(0).getX(), pillars.platforms.get(0).getWidth(), pillars.platforms.get(1).getX(), pillars.platforms.get(1).getWidth());

                universalAnim1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        mainCharacter.changeGIFBack();
                        collectiblesTotal.setText("Gems: " + collectible.getTotalCollected());
                        if(mainCharacter.isFail()) {
                            System.out.println("horray");
                            pauseScreen.makeVisible();
                            universalAnimationTimer.stop();
                        } else {
                            universalAnimationTimer.stop();
                            currentScoreCount += 1;
                            currentScore.setText("Score: " + currentScoreCount);
                            System.out.println("Done");
                            Platform.runLater(pillars);
                            Platform.runLater(mainCharacter);
                            Platform.runLater(stick);
                            Platform.runLater(L2);
                            Platform.runLater(L1);
                            Platform.runLater(L3);
                            Platform.runLater(collectible);

                            isClicked = false;
                        }
                    }
                });


            }else if(outputVal == -1){
                universalAnim2 = new Timeline();
                mainCharacter.fall(universalAnim2);

                //on finish of universalAnim2,
                //bring up the pane and the score
                universalAnim2.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        pauseScreen.makeVisible();
                    }
                });
            }else if(outputVal == 2){
                //Handle the  case for this
                mainCharacter.changeGIF();
                universalAnim1 = new Timeline();
                //broo walks till the end and falls;
                //collectible
                mainCharacter.walkStickEnd(universalAnim1, stick.getStickHeight());
                universalAnim1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        mainCharacter.changeGIFBack();

                        if(!mainCharacter.isFail()) {
                            universalAnim2 = new Timeline();
                            mainCharacter.fallStickEnd(universalAnim2);

                            universalAnim2.setOnFinished(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    pauseScreen.makeVisible();
                                }
                            });
                        }else{
                            pauseScreen.makeVisible();
                        }

                    }
                });

            }
        }
    }


    @FXML
    public void revive(){
        this.isClicked = false;
        pauseScreen.makeInvisible();
        mainCharacter.resetPos(pillars.platforms.get(0).getWidth());
        stick.resetStick();

        if(mainCharacter.isInverted()){
            System.out.println("mainInv" + mainCharacter.isInverted());
            invertChar();
            System.out.println("mainInv" + mainCharacter.isInverted());

        }

    }

    @FXML
    public void reset(){
        boolean done =  false;
        do{
            pillars.setPlatformsInitial();
            done = true;
        }while(!done);

        this.isClicked = false;
        pauseScreen.makeInvisible();
        mainCharacter.resetPos(pillars.platforms.get(0).getWidth());
        stick.resetStick(pillars.platforms.get(0).getWidth());

        if(mainCharacter.isInverted()){
            System.out.println("mainInv" + mainCharacter.isInverted());

            invertChar();
            System.out.println("mainInv" + mainCharacter.isInverted());

        }

    }

    @FXML
    public void invertChar(){
        if(isClicked)
            this.mainCharacter.invert();
    }
}
