module sdu.se9.tv2.management.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires json.simple;

    opens sdu.se9.tv2.management.system to javafx.fxml;
    exports sdu.se9.tv2.management.system;
}