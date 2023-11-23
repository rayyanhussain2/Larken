module duoforge.larken {
    requires javafx.controls;
    requires javafx.fxml;


    opens duoforge.larken to javafx.fxml;
    exports duoforge.larken;
}