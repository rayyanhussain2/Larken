package duoforge.larken;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;

public class Background implements Runnable{
    public ImageView getbg1() {
        return bg1;
    }
    private static ArrayList<ArrayList<String>> backgroundSRC = new ArrayList<>(5);

    public void setbg1(ImageView bg1) {
        this.bg1 = bg1;
    }

    public ImageView getbg2() {
        return bg2;
    }

    public void setbg2(ImageView bg2) {
        this.bg2 = bg2;
    }

    public ImageView getLayer3() {
        return layer3;
    }

    public void setLayer3(ImageView layer3) {
        this.layer3 = layer3;
    }

    private static boolean staticSet = false;
    public Background(ImageView bg1, ImageView bg2, int layerNO) {
        //set up the array. Have another function for setting it
        if(!staticSet) {
            ArrayList<String> summer1 = new ArrayList<>();
            summer1.add("summer_1_1.png");
            summer1.add("summer_1_2.png");
            summer1.add("summer_1_3.png");


            ArrayList<String> summer2 = new ArrayList<>();
            summer2.add("summer_2_1.png");
            summer2.add("summer_2_2.png");
            summer2.add("summer_2_3.png");


            ArrayList<String> summer3 = new ArrayList<>();
            summer3.add("summer_3_1.png");
            summer3.add("summer_3_2.png");
            summer3.add("summer_3_3.png");

            ArrayList<String> summer4 = new ArrayList<>();
            summer4.add("summer_4_1.png");
            summer4.add("summer_4_2.png");
            summer4.add("summer_4_3.png");

            ArrayList<String> cloud = new ArrayList<>();
            cloud.add("cloud_1.png");
            cloud.add("cloud_2.png");
            cloud.add("cloud_3.png");


            backgroundSRC.add(0, summer1);
            backgroundSRC.add(1, summer2);
            backgroundSRC.add(2, summer3);
            backgroundSRC.add(3, summer4);
            backgroundSRC.add(4, cloud);
            staticSet = true;
        }
        this.layer = layerNO;


        this.bg1 = bg1;
        this.bg2 = bg2;
        bg1.setX(0);
        bg2.setX(1138);
        l1Off = 0;
        l2Off = 0;
    }

    private Integer layer;
    public void setBG(int ind1, int ind2){
        System.out.println(backgroundSRC.get(ind1).get(this.layer));
        System.out.println(backgroundSRC.get(ind2).get(this.layer));
        this.bg1.setImage(new Image(backgroundSRC.get(ind1).get(this.layer)));
        this.bg2.setImage(new Image(backgroundSRC.get(ind2).get(this.layer)));
    }

    private ImageView bg1;
    private ImageView bg2;
    private ImageView layer3;

    private Integer l1Off;
    private Integer l2Off;

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    private Integer offset;

    //Shift Function
    @Override
    public void run() {
        l1Off -= offset;
        l2Off -= offset;
        Timeline t1 = new Timeline();
        Duration transitionDuration = Duration.millis(1000);
        KeyFrame goNextPillar = new KeyFrame(transitionDuration,
                new KeyValue(bg1.xProperty(), bg1.getX()-offset),
                new KeyValue(bg2.xProperty(), bg2.getX()-offset)
                );
        t1.getKeyFrames().add(goNextPillar);
        t1.play();

        t1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(bg1.getX() + " " + bg2.getX());
                if(bg2.getX() < 0){
                    bg1.setX(1156 + bg2.getX() - 20);
                }

                if(bg1.getX() < 0 ){
                    bg2.setX(1156 + bg1.getX()- 20);
                }
            }
        });
    }
}
