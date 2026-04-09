package seedu.address.model.transaction;

import java.util.Objects;

/**
 * Captures the column and direction that define the current transaction ordering.
 */
public final class TransactionSortState {
    private static final TransactionSortState DEFAULT_STATE =
            new TransactionSortState(TransactionSortKey.AMOUNT, SortDirection.DESCENDING);

    private final TransactionSortKey key;
    private final SortDirection direction;

    public TransactionSortState(TransactionSortKey key, SortDirection direction) {
        this.key = Objects.requireNonNull(key);
        this.direction = Objects.requireNonNull(direction);
    }

    public TransactionSortKey getKey() {
        return key;
    }

    public SortDirection getDirection() {
        return direction;
    }

    public static TransactionSortState defaultState() {
        return DEFAULT_STATE;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TransactionSortState)) {
            return false;
        }
        TransactionSortState otherState = (TransactionSortState) other;
        return key == otherState.key && direction == otherState.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, direction);
    }

    @Override
    public String toString() {
        return "TransactionSortState{" +
                "key=" + key +
                ", direction=" + direction +
                "}";
    }
}
