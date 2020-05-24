module hu.unideb.inf {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires static lombok;

    opens hu.unideb.inf to javafx.fxml;
    exports hu.unideb.inf;
}