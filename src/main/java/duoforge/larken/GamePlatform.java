package duoforge.larken;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.security.Key;
import java.util.ArrayList;
import java.util.Random;

public class GamePlatform implements Runnable{
    public final ArrayList<Rectangle> platforms;

    private final Rectangle platform1;

    public ArrayList<Rectangle> getPlatforms() {
        return platforms;
    }


    public Rectangle getPlatform1() {
        return platform1;
    }

    public Rectangle getPlatform2() {
        return platform2;
    }

    public Rectangle getPlatform3() {
        return platform3;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getContainerWidth() {
        return containerWidth;
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    public static boolean isInstanceCreated() {
        return instanceCreated;
    }

    public static void setInstanceCreated(boolean instanceCreated) {
        GamePlatform.instanceCreated = instanceCreated;
    }

    public Random getRand() {
        return rand;
    }

    public void setRand(Random rand) {
        this.rand = rand;
    }

    public Integer getAnimDuration() {
        return animDuration;
    }

    public void setAnimDuration(Integer animDuration) {
        this.animDuration = animDuration;
    }

    private final Rectangle platform2;
    private final Rectangle platform3;

    //Game details
    private final Integer width;
    private final Integer containerWidth;
    private final Integer min;
    private final Integer max;

    private static boolean instanceCreated = false;

    private Random rand;

    private Integer animDuration;

    private GamePlatform(Rectangle _platform1, Rectangle _platform2, Rectangle _platform3){
        this.platforms = new ArrayList<>();
        this.platform1 = _platform1;
        this.platform2 = _platform2;
        this.platform3 = _platform3;
        this.width = 360;
        this.containerWidth = 360 / 2;
        this.min = 40; //depends on character
        this.max = 150; // i dont know what to do with this
        this.setRand(new Random());
        this.setAnimDuration(1000);
    }

    static GamePlatform returnInstance(Rectangle _platform1, Rectangle _platform2, Rectangle _platform3){
        if (!instanceCreated){
            instanceCreated = true;
            return new GamePlatform(_platform1, _platform2, _platform3);
        }
        return null;
    }

    public void setPlatformsInitial(){
        int multiple = 0;

        this.platform1.setX(0);
        this.platform1.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;

        this.platform2.setX((multiple * containerWidth) + (rand.nextInt(containerWidth - max)));
        this.platform2.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;

        this.platform3.setX((multiple * containerWidth) + (rand.nextInt(containerWidth - max)));
        this.platform3.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;


        platforms.add(0, platform1);
        platforms.add(1, platform2);
        platforms.add(2, platform3);
    }

    @Override
    public void run() {
            Rectangle temp1 = platforms.get(0);
            Rectangle temp2 = platforms.get(1);
            Rectangle temp3 = platforms.get(2);

            Duration transitionDuration = Duration.millis(animDuration);
            Timeline timeline = new Timeline();

            KeyFrame shiftPillar = new KeyFrame(transitionDuration,
                    new KeyValue(temp1.xProperty(), -this.containerWidth),
                    new KeyValue(temp2.xProperty(), 0),
                    new KeyValue(temp3.xProperty(), (this.containerWidth) + (rand.nextInt(containerWidth - max)))

            );
            timeline.getKeyFrames().add(shiftPillar);
            timeline.play();

            //update the temp1 coordinates to go somewhere else
            timeline.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    temp1.setX((2 * containerWidth) + (rand.nextInt(containerWidth - max)));
                    temp1.setWidth(rand.nextInt(max - min + 1) + min);
                    platforms.add(0, temp2);
                    platforms.add(1, temp3);
                    platforms.add(2, temp1);
                }
            });

    }
}
