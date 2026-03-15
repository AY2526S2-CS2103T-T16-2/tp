package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class ResultDisplay extends UiPart<Region> {

    private static final String FXML = "ResultDisplay.fxml";

    @FXML
    private TextArea resultDisplay;

    /**
     * Creates a result display.
     */
    public ResultDisplay() {
        super(FXML);
    }

    /**
     * Sets the feedback text shown in the result display.
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        resultDisplay.setText(feedbackToUser);
    }

    /**
     * Updates the styling of the result display based on whether the last command succeeded.
     *
     * @param isSuccess true if the last command succeeded, false otherwise.
     */
    public void setCommandSuccess(boolean isSuccess) {
        resultDisplay.getStyleClass().removeAll("result-success", "result-error");
        resultDisplay.getStyleClass().add(isSuccess ? "result-success" : "result-error");
    }

}
