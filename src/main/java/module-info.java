module duoforge.larken {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;

    opens duoforge.larken to javafx.fxml;
    exports duoforge.larken;
}