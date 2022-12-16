package app;

import app.util.BufferedIO;
import app.util.Encryptor;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Peng You
 *
 * Initializable interface has a method that executes firstly (when this controller is loaded).
 */
public class EncryptorController implements Initializable {
    @FXML //root
    private AnchorPane mainPane;

    @FXML //Success notification slider.
    private Pane sliderPane;
    @FXML
    private Label sliderLabel;
    @FXML
    public Label sliderSeparator;

    @FXML //Encrypt and Decrypt area.
    private TextArea decryptTextA;
    @FXML
    private TextArea encryptTextA;
    @FXML
    private TextArea encryptedTextA;
    @FXML
    private TextArea decryptedTextA;
    @FXML
    private TextField keyTextField;

    //Our Ascii encryptor.
    private Encryptor encryptor;

    //Resources of another stage that we will open.
    final String InsertKeyView = "InsertKeyView.fxml";
    final String InsertKeyView_ICON = "img/insertKeyLogo.png";

    /**
     *  The first method to be executed. (Initializable  interface)
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        encryptor = new Encryptor();
        keyTextField.setText(encryptor.getAsciiKey().getKeyAsString());
    }

    //Used for the top pane drag event.
    double x, y;

    /**
     * Set our stage (window) to where the user cursor at when dragged.
     * @param mouseEvent Event input.
     */
    @FXML
    protected void dragHandler(MouseEvent mouseEvent) {
        //Gets the current stage using a node/component.
        Stage stage = (Stage) mainPane.getScene().getWindow();

        //Updating stage positions.
        stage.setX(mouseEvent.getScreenX() - x);
        stage.setY(mouseEvent.getScreenY() - y);
    }

    /**
     * Used as a complement for the dragHandler
     * Updates our position vars when detects a press event.
     * @param mouseEvent Event input.
     */
    @FXML
    protected void pressHandler(MouseEvent mouseEvent) {
        x = mouseEvent.getSceneX();
        y = mouseEvent.getSceneY();
    }

    /**
     * Exit the current stage.
     */
    @FXML
    public void exitHandler() {
        ((Stage) mainPane.getScene().getWindow()).close();
    }

    /**
     * Minimize the current stage.
     */
    @FXML
    public void minimizeHandler() {
        ((Stage) mainPane.getScene().getWindow()).setIconified(true);
    }

    /**
     * Opens a new stage view.
     * Gets the controller linked to a fxml file and sends this controller to another.
     */
    @FXML
    protected void enterKeyHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource(InsertKeyView));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);

        InsertKeyController insertController = fxmlLoader.getController();
        insertController.loadParentController(this);

        initStage(scene);
    }

    /**
     *  Initialize and show a new stage with the scene that have passed.
     */
    private void initStage(Scene scene) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Import Key");
        stage.getIcons().add(new Image(this.getClass().getResource(InsertKeyView_ICON).openStream()));
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.showAndWait();
    }

    /**
     * Method called from another controller to send information to this controller.
     * @param key Imported key by the user.
     */
    public void setKey(String key) {
        keyTextField.setText(key);
        encryptor.getAsciiKey().setCustomKey(keyTextField.getText());

        showSuccessSlider("Key imported!");

        encryptHandler();
        decryptHandler();
    }

    /**
     * Shows our slider using a translate transition (in-out animation)
     * @param successText Text to show.
     */
    private void showSuccessSlider(String successText) {
        sliderLabel.setText(successText);

        //Move animation/transition.
        TranslateTransition translate = new TranslateTransition();

        //Node to animate.
        translate.setNode(sliderPane);

        //Position where you want to end.
        translate.setToX(sliderPane.getTranslateX() - sliderPane.getWidth() + sliderSeparator.getWidth());

        //Duration of the animation.
        translate.setDuration(Duration.seconds(3));

        //In-out slide config.
        translate.setCycleCount(2);
        translate.setAutoReverse(true);

        translate.play();

        //Do nothing on finished.
        translate.setOnFinished((e) -> {
        });
    }

    /**
     * Generates a new random key and shows it on the key text field.
     */
    @FXML
    protected void newKeyHandler() {
        keyTextField.setEditable(false);

        encryptor.getAsciiKey().generateNewKeyStream();

        keyTextField.setText(encryptor.getAsciiKey().getKeyAsString());

        encryptHandler();
        decryptHandler();
    }

    /**
     * Encrypts the text contained in the text area.
     */
    @FXML
    protected void encryptHandler() {

        encryptedTextA.setText(encryptor.encrypt(encryptTextA.getText()));
    }

    /**
     * Decrypts the text contained in the text area.
     */
    @FXML
    protected void decryptHandler() {

        decryptedTextA.setText(encryptor.decrypt(decryptTextA.getText()));
    }

    /**
     * Shows a saving window, writes the current key in a .txt file
     * and saves it where the user wants in their computer.
     */
    @FXML
    protected void saveKeyHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Key");
        fileChooser.setInitialFileName("AsciiKey.txt");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Document File", "*.txt"));

        try {
            String filePath = fileChooser.showSaveDialog(mainPane.getScene().getWindow()).getAbsolutePath();

            BufferedIO bufferedIO = new BufferedIO(filePath);
            bufferedIO.writeFile(encryptor.getAsciiKey().getKeyAsString(), false, false);

            showSuccessSlider("Key saved!");
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Clear all the text from the encrypting part.
     */
    @FXML
    protected void clearEncryptor() {
        encryptTextA.clear();
        encryptedTextA.clear();

    }

    /**
     * Clear all the text from the decrypting part.
     */
    @FXML
    protected void clearDecryptor() {
        decryptTextA.clear();
        decryptedTextA.clear();
    }

    /**
     * Sets a glowing effect to the component/node that activates it.
     * @param mouseEvent Event input.
     */
    @FXML
    public void glowHandler(MouseEvent mouseEvent) {
        Node component = (Node) mouseEvent.getSource();

        Glow glow = new Glow();
        glow.setLevel(0.5);

        component.setEffect(glow);
    }

    /**
     * Removes the glowing effect to the component/node that activates it.
     * @param mouseEvent Event input.
     */
    @FXML
    public void exitGlowHandler(MouseEvent mouseEvent) {
        Node component = (Node) mouseEvent.getSource();

        component.setEffect(null);
    }

    @FXML
    public void openHelpHandler(MouseEvent mouseEvent) {
        try {
            String path = new File("").getAbsolutePath()+"\\src\\main\\java\\app\\help\\\"Ascii Encryptor User Guide (ES).pdf\"";
            new ProcessBuilder("cmd","/C",path).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
