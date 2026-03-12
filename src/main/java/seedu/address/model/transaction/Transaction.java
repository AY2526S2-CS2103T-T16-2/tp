package seedu.address.model.transaction;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Represents a loan or debt transaction recorded for a person.
 */
public class Transaction {

    public static final String MESSAGE_CONSTRAINTS = "Transaction amount must be zero or positive and description must be provided.";
    private static final String AMOUNT_CONSTRAINTS = "Transaction amount must be zero or positive.";

    private final TransactionType type;
    private final double amount;
    private final String description;

    public Transaction(TransactionType type, double amount, String description) {
        requireNonNull(type);
        requireNonNull(description);
        if (amount < 0) {
            throw new IllegalArgumentException(AMOUNT_CONSTRAINTS);
        }
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLoan() {
        return type == TransactionType.LOAN;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Transaction)) {
            return false;
        }
        Transaction otherTransaction = (Transaction) other;
        return type == otherTransaction.type
                && Double.compare(amount, otherTransaction.amount) == 0
                && description.equals(otherTransaction.description);
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        long temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("type", type)
                .add("amount", amount)
                .add("description", description)
                .toString();
    }
}
