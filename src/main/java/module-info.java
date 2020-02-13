module sdu.se9.tv2.management.system {
    requires javafx.controls;
    requires javafx.fxml;

    opens sdu.se9.tv2.management.system to javafx.fxml;
    exports sdu.se9.tv2.management.system;
}