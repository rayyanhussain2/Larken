package duoforge.larken;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public abstract class Audio implements Runnable{
    ArrayList<Audio> list = new ArrayList<>();
    private String srcPath;
    private Media src;
    private MediaPlayer mediaPlayer;

    public Audio(String s){
        srcPath = new String(s);
        src = new Media(srcPath);
        mediaPlayer = new MediaPlayer(src);
    }

    public void play(){
        mediaPlayer.stop();
        mediaPlayer.play();
    }

    public void stop(){
        mediaPlayer.stop();
    }

    public void pause(){
        mediaPlayer.pause();
    }

    public void setVolume(double V){
        mediaPlayer.setVolume(V);
    }

    public void setLoop(){
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                mediaPlayer.play();
            }
        });
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

    }
}
