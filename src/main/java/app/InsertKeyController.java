package app;

import app.model.AsciiKey;
import app.util.BufferedIO;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Peng You
 */

public class InsertKeyController {

    @FXML //root
    private AnchorPane mainPane;

    @FXML //Key input
    private TextField keyTextF;

    //Our Parent controller (who called this controller view).
    private EncryptorController parentController;

    //Alert resources
    final String ALERT_CSS = "css/AlertDialogCss.css";
    final String ALERT_HEADER = "Import failed!";
    final String ALERT_CONTENT = "Please, use a valid ascii encoding key...";
    final String ALERT_OK_BUTTON = "Continue_";


    //Used for the top pane drag event.
    double x, y;

    /**
     * Set our stage (window) to where the user cursor at when dragged.
     * @param mouseEvent Event input.
     */
    @FXML
    protected void dragHandler(MouseEvent mouseEvent) {
        Stage stage = (Stage) mainPane.getScene().getWindow();

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
     * Validates the imported key, if it's not valid, shows a custom alert with css.
     * @param key Key imported by the user.
     */
    private void validateKey(String key) {
        if (new AsciiKey().validateKey(key)) {
            backToMainStage(key);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(ALERT_HEADER);
            alert.setContentText(ALERT_CONTENT);

            //Alert customization.
            ((Stage)alert.getDialogPane().getScene().getWindow()).initStyle(StageStyle.TRANSPARENT);
            alert.getDialogPane().getScene().getStylesheets().add(this.getClass().getResource(ALERT_CSS).toExternalForm());
            ((Button)alert.getDialogPane().lookupButton(ButtonType.OK)).setText(ALERT_OK_BUTTON);

            alert.show();
        }
    }

    /**
     * Sends the successfully imported key to the parent stage using a custom method at his controller.
     * @param key Imported Key by the user.
     */
    private void backToMainStage(String key) {

        parentController.setKey(key);

        exitHandler();
    }

    /**
     * Called from the parent controller class, and loads itself in class.
     * @param parentController Class that called this method.
     */
    public void loadParentController(EncryptorController parentController) {
        this.parentController = parentController;
    }

    /**
     * Calls another method for validate the key.
     */
    @FXML
    protected void confirmButtonHandler() {
        validateKey(keyTextF.getText());
    }

    /**
     * Opens a file chooser window, allow the user choosing any .txt file from their computer.
     * After that, read and validate it.
     */
    @FXML
    protected void importKeyHandler() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import key");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Document File", "*.txt"));

        try {
            String filePath = fileChooser.showOpenDialog(mainPane.getScene().getWindow()).getAbsolutePath();

            BufferedIO bufferedIO = new BufferedIO(filePath);
            String key = bufferedIO.readFile().get(0);

            validateKey(key);

        } catch (NullPointerException ignored) {
        }

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
}
