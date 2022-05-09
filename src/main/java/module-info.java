module com.shc.serverhealthchecker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.shc.serverhealthchecker to javafx.fxml;
    exports com.shc.serverhealthchecker;
}