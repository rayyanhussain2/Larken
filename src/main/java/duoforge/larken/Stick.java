package duoforge.larken;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Stick implements Runnable {
    //Multiple sticks so not singleton.
    //not possible to create infinite sticks,so make it kinda singleton.
    //Debug info
    private Double firstX;
    private Double firstWidth;

    private Double secondX;

    private Double secondWidth;




    private Rectangle stick;
    private Integer animDuration;
    private Integer stickFinalHeight;

    public Timeline getStickAnimation() {
        return stickAnimation;
    }

    public void setStickAnimation(Timeline stickAnimation) {
        this.stickAnimation = stickAnimation;
    }

    private Timeline stickAnimation;

    public Rectangle getStick() {
        return stick;
    }

    public void setStick(Rectangle stick) {
        this.stick = stick;
    }

    public static boolean isIsInstantiated() {
        return isInstantiated;
    }

    public static void setIsInstantiated(boolean isInstantiated) {
        Stick.isInstantiated = isInstantiated;
    }

    private static boolean isInstantiated = false;

    private Double ogX;
    private Double ogHeight;
    private Double ogWidth;
    private Double ogY;
    private Double ogRotate;


    public double getStickHeight(){
        return stick.getHeight();
    }

    private Stick(Rectangle _stick, double xPos){
        this.stick = _stick;
        this.stickAnimation = new Timeline();
        this.animDuration = 1000;
        this.stick.setX(xPos);
        this.stickFinalHeight = 500;
        this.ogY = this.stick.getY();
        this.ogX = this.stick.getX();
        this.ogWidth = this.stick.getWidth();
        this.ogHeight = this.stick.getHeight();
        this.ogRotate = this.stick.getRotate();


    }
    public static Stick getInstance(Rectangle _stick, double xPos){
        if(!isInstantiated){
            isInstantiated = true;
            return new Stick(_stick, xPos);
        }
        return null;
    }

    public void elongateStick(){
        Duration transitionDuration1 = Duration.millis(animDuration);
        double originalY = this.stick.getY();
        this.stick.setRotate(0);
        stickAnimation = new Timeline();
        KeyFrame elongate = new KeyFrame(transitionDuration1,
                new KeyValue(this.stick.heightProperty(), stickFinalHeight),
                new KeyValue(this.stick.yProperty(), -stickFinalHeight)
        );
        stickAnimation.getKeyFrames().add(elongate);
        stickAnimation.play();
    }


    public Integer pauseStick(double firstX, double firstWidth, double secondX, double secondWidth){
        //updating imp attributes
        this.firstX = firstX;
        this.firstWidth = firstWidth;
        this.secondWidth = secondWidth;
        this.secondX = secondX;

        //pause and rotate
        stickAnimation.pause();
        Duration transitionDuration1 = Duration.millis(50);
        double originalY = this.stick.getY();
        double originalX = this.stick.getX();

        if(stick.getHeight() < secondX - firstWidth){
            KeyFrame rotate = new KeyFrame(Duration.millis(100),
                    new KeyValue(this.stick.rotateProperty(), 180),
                    new KeyValue(this.stick.xProperty(), stick.getHeight()/2 + firstWidth),
                    new KeyValue(this.stick.yProperty(), 1000)

            );
            stickAnimation = new Timeline();
            stickAnimation.getKeyFrames().add(rotate);
            stickAnimation.play();
            return -1;
        }else{
            KeyFrame rotate = new KeyFrame(transitionDuration1,
                    new KeyValue(this.stick.rotateProperty(), 90),
                    new KeyValue(this.stick.xProperty(), stick.getHeight()/2 + firstWidth),
                    new KeyValue(this.stick.yProperty(), -stick.getHeight()/2)

            );
            stickAnimation = new Timeline();
            stickAnimation.getKeyFrames().add(rotate);
            stickAnimation.play();
            if(stick.getHeight() > (secondX - firstWidth + secondWidth)){
                return 2;
            }else{
                return 1;
            }
        }
    }


    @Override
    public void run() {
        stick.setVisible(false);

        Duration transitionDuration1 = Duration.millis(animDuration);
        Timeline timeline1 = new Timeline();

        KeyFrame walkTillAir = new KeyFrame(transitionDuration1,
                new KeyValue(stick.xProperty(), secondWidth)
        );
        timeline1.getKeyFrames().add(walkTillAir);
        timeline1.play();


        timeline1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stick.setVisible(true);
                stick.setY(ogY);
                stick.setX(secondWidth);
                stick.setHeight(0);
                stick.setWidth((ogWidth));
                stick.setRotate(0);
            }
        });
    }

    public void resetStick(){
        stick.setVisible(true);
        stick.setY(ogY);
        stick.setX(firstWidth);
        stick.setHeight(0);
        stick.setWidth((ogWidth));
        stick.setRotate(0);
    }

    public void resetStick(double xPos){
        stick.setVisible(true);
        stick.setY(ogY);
        stick.setX(xPos);
        stick.setHeight(0);
        stick.setWidth((ogWidth));
        stick.setRotate(0);
    }

}
