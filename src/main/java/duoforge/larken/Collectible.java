package duoforge.larken;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class Collectible implements Runnable {

    public Integer isBottom() {
        return isBottom;
    }

    public Integer getTotalCollected() {
        return totalCollected;
    }

    public void setTotalCollected() {
        if(isCollected) {
            this.totalCollected += 1;
            this.setCollected(false);
        }
    }

    private Integer totalCollected;

    public void initSet(Integer set){
        totalCollected = set;
    }
    public boolean isCollected() {
        return isCollected;
    }


    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    private boolean isCollected;

    public void setIsBottom(Integer isBottom) {
        this.isBottom = isBottom;
    }

    private Integer isBottom;
    public ImageView getCollectible() {
        return collectible;
    }

    public void setCollectible(ImageView collectible) {
        this.collectible = collectible;
    }

    ImageView collectible;


    public Collectible(ImageView collectible) {
        this.collectible = collectible;
        this.collectible.setVisible(false);
        this.totalCollected = 0;
        this.isBottom = 0;
    }

    public void set(double firstWidth, double secondX){
        isCollected = false;
        Random rand = new Random();
        Double random = rand.nextDouble(0, 100);

        if(random > 80){
            //place up
            this.collectible.setVisible(true);

            this.collectible.setY(-20);
            this.collectible.setX(rand.nextDouble(firstWidth + 10, secondX - 10));
            this.isBottom = 1;
        }else if(random < 20){
            this.collectible.setVisible(true);

            //place down
            this.collectible.setY(-20);
            this.collectible.setX(rand.nextDouble(firstWidth + 10, secondX - 10));
            this.isBottom = 1;

        }else{
            this.collectible.setVisible(false);
        }
        System.out.println("Jewel loc: " + this.collectible.getX());
    }

    @Override
    public void run() {
        Duration transitionDuration1 = Duration.millis(1000);
        Timeline timeline1 = new Timeline();

        KeyFrame shiftBack = new KeyFrame(transitionDuration1,
                new KeyValue(collectible.xProperty(), -collectible.getFitWidth() - 10)

        );
        timeline1.getKeyFrames().add(shiftBack);
        timeline1.play();
    }

    public Double getCollectibleX(){
        return this.collectible.getX();
    }

    public void makeInvisible(){
        this.collectible.setVisible(false);
    }

    public void setX(Double xPos){
        this.collectible.setX(xPos);
    }

}
