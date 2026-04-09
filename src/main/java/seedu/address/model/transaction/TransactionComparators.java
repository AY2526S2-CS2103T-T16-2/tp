package seedu.address.model.transaction;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Exposes comparators that mirror the UI sort order for transactions.
 */
public final class TransactionComparators {
    private TransactionComparators() {
    }

    public static Comparator<Transaction> comparatorFor(TransactionSortState state, Person currentPerson) {
        Comparator<Transaction> base = comparatorForKey(state.getKey(), currentPerson);
        return state.getDirection() == SortDirection.DESCENDING ? base.reversed() : base;
    }

    private static Comparator<Transaction> comparatorForKey(TransactionSortKey key, Person currentPerson) {
        return switch (key) {
        case DIRECTION -> Comparator.comparing(tx -> TransactionDisplayHelper.directionText(currentPerson, tx),
                String.CASE_INSENSITIVE_ORDER);
        case OTHER_PARTY -> Comparator.comparing(tx -> TransactionDisplayHelper.otherPartyName(currentPerson, tx),
                String.CASE_INSENSITIVE_ORDER);
        case AMOUNT -> Comparator.comparingDouble(tx -> Math.abs(tx.getCurrAmount()));
        case DESCRIPTION -> Comparator.comparing(Transaction::getDescription, String.CASE_INSENSITIVE_ORDER);
        case STATUS -> Comparator.comparing(Transaction::isSettled)
                .thenComparing(Transaction::getDescription, String.CASE_INSENSITIVE_ORDER);
        case DATE -> Comparator.comparing(Transaction::getDate);
        };
    }
}
