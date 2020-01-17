package edVokabelTrainer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static final double version = 0.28;
    private static final String appName = "Vokabeltrainer";
    public static final String parentPath = "bin/apps/" + appName + "/";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/MainWindow.fxml"));
        primaryStage.setTitle("ED Vokabeltrainer - Version " + version);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
