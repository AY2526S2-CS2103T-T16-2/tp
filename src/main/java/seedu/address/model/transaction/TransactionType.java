package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

/**
 * Represents the category of a transaction between the user and another person.
 */
public enum TransactionType {
    DEBT,
    LOAN;

    public static final String MESSAGE_CONSTRAINTS = "Transaction type must be DEBT or LOAN.";

    /**
     * Parses the input string into a {@code TransactionType}.
     *
     * @throws IllegalValueException if the type is invalid.
     */
    public static TransactionType fromString(String type) throws IllegalValueException {
        requireNonNull(type);
        try {
            return TransactionType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
    }
}
