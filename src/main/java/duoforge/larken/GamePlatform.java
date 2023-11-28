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

public class GamePlatform{
    private final ArrayList<Rectangle> platforms = new ArrayList<>();

    private final Rectangle platform1;
    private final Rectangle platform2;
    private final Rectangle platform3;
    private final Rectangle platform4;

    public GamePlatform(Rectangle iplatform1, Rectangle iplatform2, Rectangle iplatform3, Rectangle iplatform4){
        this.platform1 = iplatform1;
        this.platform2 = iplatform2;
        this.platform3 = iplatform3;
        this.platform4 = iplatform4;
    }

    public void setPlatformsInitial(){
        int width = 840;
        int containerwidth = 280;
        int multiple = 0;
        int min = 40;
        int max = 120;
        Random rand = new Random();

        this.platform1.setX(0);
        this.platform1.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;

        this.platform2.setX((multiple * containerwidth) + (rand.nextInt(160)));
        this.platform2.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;

        this.platform3.setX((multiple * containerwidth) + (rand.nextInt(160)));
        this.platform3.setWidth(rand.nextInt(max - min + 1) + min);
        multiple += 1;

        this.platform4.setX((multiple * containerwidth) + (rand.nextInt(160)));
        this.platform4.setWidth(rand.nextInt(max - min + 1) + min);

        platforms.add(platform1);
        platforms.add(platform2);
        platforms.add(platform3);
        platforms.add(platform4);
    }

    public void shiftPlatforms(){
        Rectangle temp1 = platforms.get(0);
        Rectangle temp2 = platforms.get(1);
        Rectangle temp3 = platforms.get(2);
        Rectangle temp4 = platforms.get(3);

        Duration transitionDuration = Duration.millis(1000);
        Timeline timeline = new Timeline();

        KeyFrame shiftPillar = new KeyFrame(transitionDuration,
                new KeyValue(temp1.xProperty(), -280),
                new KeyValue(temp2.xProperty(), 0),
                new KeyValue(temp3.xProperty(), 280),
                new KeyValue(temp4.xProperty(), 2 * 280)

        );
        timeline.getKeyFrames().add(shiftPillar);
        timeline.play();


        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Random rand = new Random();
                temp1.setX((5 * 168) + (rand.nextInt(49)));
                temp1.setWidth(rand.nextInt(121 - 40 + 1) + 40);

                platforms.add(0, temp2);
                platforms.add(1, temp3);
                platforms.add(2, temp4);
                platforms.add(5, temp1);
            }
        });

    }

}
