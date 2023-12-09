package duoforge.larken;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class PauseScreen {
    Pane background;

    public Pane getBackground() {
        return background;
    }

    public void setBackground(Pane background) {
        this.background = background;
    }

    public Button getReset() {
        return reset;
    }

    public void setReset(Button reset) {
        this.reset = reset;
    }

    public Button getRevive() {
        return revive;
    }

    public void setRevive(Button revive) {
        this.revive = revive;
    }

    Button reset;
    Button revive;

    public PauseScreen(Pane background, Button reset, Button revive) {
        this.background = background;
        this.reset = reset;
        this.revive = revive;
        this.background.setPrefWidth(360);
        this.background.setPrefHeight(0);

    }

    public void makeVisible(){
        this.reset.setLayoutX(150);
        this.reset.setLayoutY(150);
        this.revive.setLayoutX(200);
        this.revive.setLayoutY(150);
        this.background.setPrefHeight(640);

    }

    public void makeInvisible(){
        this.reset.setLayoutX(-150);
        this.reset.setLayoutY(-150);
        this.revive.setLayoutX(-200);
        this.revive.setLayoutY(-150);
        this.background.setPrefHeight(-640);
    }
}
