package duoforge.larken;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class Scene1 implements Initializable {


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
    Text currentScore = new Text();

    @FXML
    Text collectiblesTotal = new Text();

    @FXML
    ImageView beatScore = new ImageView();

    @FXML
    Text currHS = new Text();


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

    @FXML
    ImageView play = new ImageView();

    @FXML
    ImageView heroImage = new ImageView();
    @FXML
    ImageView pauseButton = new ImageView();


    @FXML
    ImageView newHSImage = new ImageView();
    @FXML
    ImageView failedImage = new ImageView();

    @FXML
    ImageView reset = new ImageView();
    @FXML
    ImageView revive = new ImageView();

    //Classes
    GamePlatform pillars;
    PlayableCharacter mainCharacter;
    Timeline universalAnim1;
    Timeline universalAnim2;

    Integer HighScore;
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

    SoundFX collect;
    SoundFX end;

    BGM main;

    BGM game;

    SoundFX elongate;
    boolean initialPhase;

    Integer totalCoins;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //setting fonts
        collectiblesTotal.setFont(Font.font(24));
        collectiblesTotal.setText("Coins: -");

        currentScore.setFont(Font.font(24));
        currentScore.setText("Score: -");
        //read the HS
        try {
            BufferedReader f = new BufferedReader(new FileReader("results.txt"));
            HighScore = Integer.parseInt(f.readLine());
            totalCoins = Integer.parseInt(f.readLine());
            f.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


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

        pauseScreen = new PauseScreen(pauseMainPane, play, heroImage, beatScore, currHS, pauseButton, newHSImage, failedImage, reset, revive);

        collectible = new Collectible(collectibleImage);
        cherries = 0;

        collect = new SoundFX(Objects.requireNonNull(getClass().getResource("/cherry.wav")).toExternalForm(), 80);
        end = new SoundFX(Objects.requireNonNull(getClass().getResource("/gameover.wav")).toExternalForm(), 80);
        game = new BGM(Objects.requireNonNull(getClass().getResource("/bg_music_1.mp3")).toExternalForm(), 80);
        main = new BGM(Objects.requireNonNull(getClass().getResource("/bg_music_2.mp3")).toExternalForm(), 80);
        elongate = new SoundFX(Objects.requireNonNull(getClass().getResource("/extending_rod.wav")).toExternalForm(), 80);

        //Display mainMenu
        initialPhase = true;
        mainMenu(HighScore);
        main.play();
        //game.play();

        //set gems initial value
        collectible.initSet(totalCoins);
    }

    @FXML
    public void onGameInput() throws InterruptedException { //facade
        //Platform.runLater(pillars);
        //mainCharacter.goNextPillar(pillars.getPlatform2().getX() + pillars.getPlatform2().getWidth() - mainCharacter.getWidth());
        //mainCharacter.fall();
        //Facade
        if(!isClicked && !initialPhase) {
            stick.elongateStick();
            elongate.play();
            isClicked = true;
            collectible.set(pillars.platforms.get(0).getWidth(), pillars.platforms.get(1).getX());
        }
    }



    @FXML
    public void onGameInputRelease() throws InterruptedException {
        //Facade
        if(isClicked && !initialPhase){
            elongate.stop();
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
                mainCharacter.goNextPillar(collect, collectible, universalAnim1, pillars.getPlatform2().getX() + pillars.getPlatform2().getWidth() - mainCharacter.getWidth(), pillars.platforms.get(0).getX(), pillars.platforms.get(0).getWidth(), pillars.platforms.get(1).getX(), pillars.platforms.get(1).getWidth());

                universalAnim1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        mainCharacter.changeGIFBack();
                        collectiblesTotal.setText("Coins: " + collectible.getTotalCollected());
                        if(mainCharacter.isFail()) {
                            System.out.println("horray");
                            pauseScreen.makeVisible(currentScoreCount > HighScore);
                            initialPhase = true;
                            universalAnimationTimer.stop();
                            //fail1
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

                            //make this false only when one of the transitions done
                            isClicked = false;
                        }
                    }
                });


            }else if(outputVal == -1){
                universalAnim2 = new Timeline();
                mainCharacter.fall(universalAnim2);
                Platform.runLater(end);
                //on finish of universalAnim2,
                //bring up the pane and the score
                universalAnim2.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(currentScoreCount > HighScore) {
                            HighScore = currentScoreCount;
                            System.out.println("New hs");
                        }
                        //read the HS
                        try {
                            BufferedWriter f = new BufferedWriter(new FileWriter("results.txt"));
                            f.write(String.valueOf(HighScore));
                            f.write("\n");
                            f.write(String.valueOf(collectible.getTotalCollected()));
                            f.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        pauseScreen.makeVisible(currentScoreCount > HighScore);
                        initialPhase = true;
                    }
                });

            }else if(outputVal == 2){
                //Handle the  case for this
                mainCharacter.changeGIF();
                universalAnim1 = new Timeline();
                //broo walks till the end and falls;
                //collectible
                mainCharacter.walkStickEnd(collect, universalAnim1, stick.getStickHeight());
                universalAnim1.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Platform.runLater(end);

                        mainCharacter.changeGIFBack();

                        if(!mainCharacter.isFail()){
                            universalAnim2 = new Timeline();
                            mainCharacter.fallStickEnd(universalAnim2);

                            universalAnim2.setOnFinished(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    pauseScreen.makeVisible(currentScoreCount > HighScore);
                                    initialPhase = true;

                                    //fail 2
                                    if(currentScoreCount > HighScore) {
                                        HighScore = currentScoreCount;
                                        System.out.println("New hs");
                                    }
                                    //read the HS
                                    try {
                                        BufferedWriter f = new BufferedWriter(new FileWriter("results.txt"));
                                        f.write(String.valueOf(HighScore));
                                        f.write("\n");
                                        f.write(String.valueOf(collectible.getTotalCollected()));
                                        f.close();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }else{
                            pauseScreen.makeVisible(currentScoreCount > HighScore);
                            initialPhase = true;
                        }

                    }
                });

            }
        }
    }


    @FXML
    public void revive(){
        if(collectible.getTotalCollected() >= 5) {
            initialPhase = false;
            this.isClicked = false;
            pauseScreen.makeInvisible();
            mainCharacter.resetPos(pillars.platforms.get(0).getWidth());
            stick.resetStick();

            if (mainCharacter.isInverted()) {
                System.out.println("mainInv" + mainCharacter.isInverted());
                invertChar();
                System.out.println("mainInv" + mainCharacter.isInverted());

            }
            collectible.initSet(collectible.getTotalCollected() -  5);
            collectiblesTotal.setText("Coins: " + collectible.getTotalCollected());
        }

    }

    @FXML
    public void reset(){
        currentScoreCount = 0;
        initialPhase = false;
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

        Random rand = new Random();
        Integer firstRand = rand.nextInt(0, 5);
        Integer secondRand = rand.nextInt(0, 5);

        L1.setBG(firstRand, secondRand);
        L2.setBG(firstRand, secondRand);
        L3.setBG(firstRand, secondRand);

        currentScore.setText("Score: -");
    }

    @FXML
    public void invertChar(){
        if(isClicked)
            this.mainCharacter.invert();
    }

    public void mainMenu(Integer HS){
        pauseScreen.mainMenu(HS);
    }

    @FXML
    public void mainMenu(){
        pauseScreen.mainMenu(HighScore);
    }

    public void playButton(){
        pauseScreen.mainMenuDestroy();
        this.initialPhase = false;
        main.stop();
        game.play();
    }
}
