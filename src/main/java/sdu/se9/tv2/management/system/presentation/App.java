package sdu.se9.tv2.management.system.presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("main"), 640, 480);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    static Scene getScene () {
        return scene;
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("/sdu/se9/tv2/management/system/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void load() {
        launch();
    }

    static void setPage (String fxml) throws IOException {
        Parent page = loadFXML(fxml);

        BorderPane mainBorderPane = (BorderPane) scene.lookup("#mainBorderPane");

        mainBorderPane.setCenter(page);
    }
}