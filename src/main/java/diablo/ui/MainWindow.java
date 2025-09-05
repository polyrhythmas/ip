package diablo.ui;

import diablo.exception.DiabloException;
import diablo.task.Deadline;
import diablo.task.Event;
import diablo.task.Task;
import diablo.task.ToDo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Diablo diablo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/diabloUser.png"));
    private Image diabloImage = new Image(this.getClass().getResourceAsStream("/images/diablo.png"));
    private String greeting = "Hello! I'm Diablo.\nWhat can I do for you?";

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getDiabloDialog(greeting, diabloImage)
        );

    }

    /** Injects the Diablo instance */
    public void setDiablo(Diablo d) {
        diablo = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Diablo's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String[] output = diablo.getOutput(input);
        diablo.addToStorage();

        if (output[0].equals("1")) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDiabloDialog(output[1], diabloImage)
            );

        } else {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDiabloDialog(output[1], diabloImage)
            );
            userInput.clear();
    }
    }
}

