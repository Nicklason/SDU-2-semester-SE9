module sdu.se9.tv2.management.system {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.postgresql.jdbc;
    requires java.sql;

    opens sdu.se9.tv2.management.system.presentation to javafx.fxml;
    exports sdu.se9.tv2.management.system.presentation;
}