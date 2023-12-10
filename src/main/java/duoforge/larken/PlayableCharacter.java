package duoforge.larken;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PlayableCharacter implements Runnable{
    private Double firstX;
    private Double firstWidth;

    private Double secondX;

    private Double secondWidth;
    ImageView character;
    private static boolean isInstantiated = false;
    private Integer animDuration;
    Timeline universalAnim1, universalAnim2;
    private Double ogWidth;
    public boolean isFail() {
        return fail;
    }

    private boolean fail;
    public double getWidth(){
        return character.getFitWidth();
    }

    private PlayableCharacter(ImageView _mainCharacter, double xPos){
        this.character = _mainCharacter;
        this.character.setX(xPos - this.character.getFitWidth());
        animDuration = 1000;
        ogWidth = this.character.getFitWidth();
    }

    static PlayableCharacter returnInstance(ImageView _mainCharacter, double xPos){
        if (!isInstantiated){
            isInstantiated = true;
            return new PlayableCharacter(_mainCharacter, xPos);
        }
        return null;
    }

    public void changeGIF(){
        Double tempWidth = this.character.getFitWidth();
        Double tempHeight = this.character.getFitHeight();
        this.character.setImage(new Image("character_1_run.gif"));
        this.character.setFitWidth(ogWidth * 2);
        this.character.setFitHeight(tempHeight);
    }
    public void changeGIFBack(){
        Double tempWidth = this.character.getFitWidth();
        Double tempHeight = this.character.getFitHeight();
        this.character.setImage(new Image("character_1_idle.gif"));
        this.character.setFitWidth(ogWidth);
        this.character.setFitHeight(tempHeight);
    }


    //Moves to the next pillar
   /*
        how can we communicate the pillar information to character.
        Have a dependendcy relation
        and then how will you update doe.
        But character movement need not to be multithreaded only shiftall
        Pass the pilars class to use its information
   */
    //Fix this for accounting up and down
    public void walkStickEnd(SoundFX s, Timeline t1, double stickHeight){
        fail = false;
        Duration transitionDuration = Duration.millis(animDuration);
        KeyFrame goNextPillar = new KeyFrame(transitionDuration,
                new KeyValue(character.xProperty(), character.getX() + stickHeight + 20)
        );
        t1.getKeyFrames().add(goNextPillar);
        t1.play();

        Timeline t2 = new Timeline();
        at = new AnimationTimer() {
            @Override
            public void handle(long l) {
                System.out.println("Debug x: " + character.getX());
                if(character.getScaleY() == -1 && character.getX() >= (secondX - character.getFitWidth()) ){
                    Platform.runLater(s);
                    t1.stop();
                    KeyFrame empty = new KeyFrame(transitionDuration, new KeyValue(character.yProperty(), 1000));
                    t1.getKeyFrames().remove(0);
                    t1.getKeyFrames().add(empty);
                    t1.play();


                    KeyFrame fall = new KeyFrame(transitionDuration,
                            new KeyValue(character.yProperty(), 1000)
                    );
                    t2.getKeyFrames().add(fall);
                    t2.play();
                    fail = true;
                }
            }
        };
    }

    public void fallStickEnd(Timeline t1) {
        Duration transitionDuration = Duration.millis(animDuration);
        KeyFrame goNextPillar = new KeyFrame(transitionDuration,
                new KeyValue(character.yProperty(), 1000)
        );
        t1.getKeyFrames().add(goNextPillar);
        t1.play();
    }

        //Fix this for accounting up and down
    AnimationTimer at;
    public void goNextPillar(SoundFX s, Collectible c1, Timeline t1, double xCoordinates, double firstX, double firstWidth, double secondX, double secondWidth){
        //updating imp attributes
        fail = false;
        this.firstX = firstX;
        this.firstWidth = firstWidth;
        this.secondWidth = secondWidth;
        this.secondX = secondX;
        Duration transitionDuration = Duration.millis(animDuration);

        KeyFrame goNextPillar = new KeyFrame(transitionDuration,
                new KeyValue(character.xProperty(), secondX + secondWidth - (this.character.getFitWidth() * 2/3))
        );
        t1.getKeyFrames().add(goNextPillar);
        Timeline t2 = new Timeline();
        at = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //System.out.println("Debug x: " + character.getX());
                if(character.getScaleY() == c1.isBottom() && character.getX() > c1.getCollectibleX()){
                    Platform.runLater(s);
                    c1.makeInvisible();
                    c1.setCollected(true);
                    c1.setTotalCollected();
                    c1.setIsBottom(-c1.isBottom());
                    c1.setX(400.0);
                }
                if(character.getScaleY() == -1 && character.getX() >= (secondX - character.getFitWidth()) ){
                    t1.stop();
                    KeyFrame empty = new KeyFrame(transitionDuration, new KeyValue(character.yProperty(), 1000));
                    t1.getKeyFrames().remove(0);
                    t1.getKeyFrames().add(empty);
                    t1.play();


                    KeyFrame fall = new KeyFrame(transitionDuration,
                            new KeyValue(character.yProperty(), 1000),
                            new KeyValue(character.scaleYProperty(), 1)
                    );
                    t2.getKeyFrames().add(fall);
                    t2.play();
                    fail = true;
                }

            }
        };
        t1.play();
        at.start();

        t2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                at.stop();
            }
        });
    }

    //Falls down - fix it
    public void fall(Timeline t1){
        Duration transitionDuration1 = Duration.millis(1500);

        KeyFrame walkTillAir = new KeyFrame(transitionDuration1,
                new KeyValue(character.xProperty(), character.getX() + character.getFitWidth())
        );
        KeyFrame fall = new KeyFrame(transitionDuration1, new KeyValue(character.yProperty(), 1000));
        t1.getKeyFrames().add(walkTillAir);
        t1.getKeyFrames().add(fall);
        t1.play();
    }

    @Override
    public void run() {
        Duration transitionDuration1 = Duration.millis(animDuration);
        Timeline timeline1 = new Timeline();

        KeyFrame walkTillAir = new KeyFrame(transitionDuration1,
                new KeyValue(character.xProperty(), secondWidth - character.getFitWidth())

        );
        timeline1.getKeyFrames().add(walkTillAir);
        timeline1.play();
        System.out.println(character.getX());
    }

    public void resetPos(Double xPos){
        this.character.setY(0);
        this.character.setX(xPos - this.character.getFitWidth());
    }

    private Integer scrollCount = 0;

    public boolean isInverted() {
        return isInverted;
    }

    public void secretInvert(){
        this.character.setScaleY(1);
    }
    private boolean isInverted = false;
    public void invert(){
        if(scrollCount % 2 == 0) {
            this.character.setScaleY(-1);
            this.character.setY(this.character.getFitHeight());
            scrollCount += 1;
            isInverted = true;
        }else{
            this.character.setScaleY(1);
            this.character.setY(0);
            scrollCount -= 1;
            isInverted = false;
        }
    }

}
