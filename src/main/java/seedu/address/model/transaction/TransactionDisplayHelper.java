package seedu.address.model.transaction;

import seedu.address.model.person.Person;

/**
 * Provides utility helpers for formatting transactions according to the current view context.
 */
public final class TransactionDisplayHelper {
    private static final String DIRECTION_OWE = "Owe";
    private static final String DIRECTION_LENT = "Lent";

    private TransactionDisplayHelper() {
    }

    /**
     * Returns the direction label shown for the current person in the given transaction.
     */
    public static String directionText(Person currentPerson, Transaction transaction) {
        if (currentPerson == null || transaction == null) {
            return "";
        }
        if (transaction.getDebtor().isSamePerson(currentPerson)) {
            return DIRECTION_OWE;
        }
        if (transaction.getCreditor().isSamePerson(currentPerson)) {
            return DIRECTION_LENT;
        }
        return "";
    }

    /**
     * Returns the name of the counterparty shown for the current person in the given transaction.
     */
    public static String otherPartyName(Person currentPerson, Transaction transaction) {
        if (currentPerson == null || transaction == null) {
            return "";
        }
        if (transaction.getDebtor().isSamePerson(currentPerson)) {
            return transaction.getCreditor().getName().fullName;
        }
        if (transaction.getCreditor().isSamePerson(currentPerson)) {
            return transaction.getDebtor().getName().fullName;
        }
        return "";
    }
}
