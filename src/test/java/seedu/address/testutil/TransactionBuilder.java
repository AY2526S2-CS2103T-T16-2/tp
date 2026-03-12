package seedu.address.testutil;

import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;

/**
 * A utility class to help build {@link Transaction} objects.
 */
public class TransactionBuilder {

    public static final TransactionType DEFAULT_TYPE = TransactionType.LOAN;
    public static final double DEFAULT_AMOUNT = 10.0;
    public static final String DEFAULT_DESCRIPTION = "Default transaction";

    private TransactionType type;
    private double amount;
    private String description;

    public TransactionBuilder() {
        type = DEFAULT_TYPE;
        amount = DEFAULT_AMOUNT;
        description = DEFAULT_DESCRIPTION;
    }

    public TransactionBuilder(Transaction transactionToCopy) {
        type = transactionToCopy.getType();
        amount = transactionToCopy.getAmount();
        description = transactionToCopy.getDescription();
    }

    public TransactionBuilder withType(TransactionType type) {
        this.type = type;
        return this;
    }

    public TransactionBuilder withAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public Transaction build() {
        return new Transaction(type, amount, description);
    }
}
