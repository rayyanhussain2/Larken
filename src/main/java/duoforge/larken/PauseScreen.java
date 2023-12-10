package duoforge.larken;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PauseScreen {
    Pane background;

    public Pane getBackground() {
        return background;
    }

    public void setBackground(Pane background) {
        this.background = background;
    }

    public ImageView getReset() {
        return reset;
    }

    public void setReset(ImageView reset) {
        this.reset = reset;
    }

    public ImageView getRevive() {
        return revive;
    }

    public void setRevive(ImageView revive) {
        this.revive = revive;
    }

    private ImageView reset;
    private ImageView revive;

    private ImageView play;

    private ImageView heroText;

    private ImageView Failed;

    private ImageView beatScore;

    private ImageView newHS;

    private Text currHS;

    private ImageView pauseButton;



    public void mainMenu(Integer score){
        this.background.setPrefHeight(640);
        this.currHS.setText(String.valueOf(score));
        this.currHS.setFont(new Font(30));
        this.currHS.setX(10);
        this.pauseButton.setVisible(false);
        this.Failed.setVisible(false);
        this.newHS.setVisible(false);
        this.reset.setVisible(false);
        this.revive.setVisible(false);
    }

    public void mainMenuDestroy(){
        this.background.setPrefHeight(0);
        this.play.setVisible(false);
        this.heroText.setVisible(false);
        this.beatScore.setVisible(false);
        this.currHS.setVisible(false);
        this.pauseButton.setVisible(true);
    }


    public PauseScreen(Pane background,ImageView _play, ImageView heroImage, ImageView _beatScore, Text _currHS, ImageView pauseButton
                        , ImageView newHSImage, ImageView failedImage, ImageView reset, ImageView revive) {
        this.background = background;
        this.reset = reset;
        this.revive = revive;
        this.background.setPrefWidth(360);
        this.background.setPrefHeight(0);
        this.play = _play;
        this.heroText = heroImage;
        this.beatScore = _beatScore;
        this.currHS = _currHS;
        this.pauseButton = pauseButton;
        this.newHS = newHSImage;
        this.Failed = failedImage;
    }

    public void makeVisible(boolean beatScore){
        this.Failed.setVisible(true);
        this.reset.setVisible(true);
        this.revive.setVisible(true);
        this.background.setPrefHeight(640);
        if(beatScore){
            this.newHS.setVisible(true);
        }
    }

    public void makeInvisible(){
        this.Failed.setVisible(false);
        this.reset.setVisible(false);
        this.revive.setVisible(false);
        this.background.setPrefHeight(0);
        this.newHS.setVisible(false);
    }
}
