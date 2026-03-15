package seedu.address.ui;

import static java.util.Objects.requireNonNull;

/**
 * Utility methods for formatting UI strings.
 * <p>
 * Kept as a pure (non-JavaFX) helper so it can be covered by unit tests without requiring a GUI runtime.
 */
public final class UiMessages {

    private UiMessages() {}

    /**
     * Formats a success message for display in the result display.
     *
     * @param feedback raw feedback text
     * @return formatted success message
     */
    public static String success(String feedback) {
        requireNonNull(feedback);
        return "[SUCCESS] " + feedback;
    }

    /**
     * Formats an error message for display in the result display.
     *
     * @param message raw error message
     * @return formatted error message
     */
    public static String error(String message) {
        requireNonNull(message);
        return "[ERROR] " + message;
    }

    /**
     * Formats the active contacts title string.
     *
     * @param count number of active contacts
     * @return formatted title
     */
    public static String activeContactsTitle(int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative");
        }
        return "Active Contacts (" + count + ")";
    }

    /**
     * Formats the transactions panel title string.
     *
     * @param transactionCount number of transactions displayed
     * @param personName selected person's name
     * @return formatted title
     */
    public static String transactionsTitle(int transactionCount, String personName) {
        requireNonNull(personName);
        if (transactionCount < 0) {
            throw new IllegalArgumentException("transactionCount must be non-negative");
        }
        return "Transactions (" + transactionCount + ") - " + personName;
    }
}
