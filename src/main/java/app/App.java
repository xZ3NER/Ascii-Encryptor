package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @author Peng You
 */

//JavaFX GUI structure: Stage -> Scene -> Node/Components
public class App extends Application {
    final String MAIN_STAGE =  "EncryptorView.fxml";
    final String STAGE_ICON = "img/encryptorLogo.png";
    @Override
    public void start(Stage stage) throws IOException {
        //Loads our main view (fxml file) to a scene.
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(MAIN_STAGE));
        Scene scene = new Scene(fxmlLoader.load());

        //Fills our scene white corners to transparent.
        //(Because our root node (AnchorPane) has rounded borders)
        scene.setFill(Color.TRANSPARENT);

        //Set the stage top bar to transparent.
        stage.initStyle(StageStyle.TRANSPARENT);

        //Set the icon.
        stage.getIcons().add(new Image(this.getClass().getResource(STAGE_ICON).openStream()));

        stage.setTitle("Encryption Program");
        stage.setResizable(false);

        //Set our scene (fxml view) to the stage (window view)
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}