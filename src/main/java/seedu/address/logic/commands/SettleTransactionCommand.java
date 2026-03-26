package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Marks a specific transaction as settled without deleting it.
 */
public class SettleTransactionCommand extends Command {

    public static final String COMMAND_WORD = "settle";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks a transaction as settled using the displayed person and transaction indexes.\n"
            + "Parameters: PERSON_INDEX t/TRANSACTION_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " 1 t/2";

    public static final String MESSAGE_SUCCESS = "Transaction #%1$d settled for %2$s.";
    public static final String MESSAGE_ALREADY_SETTLED = "Transaction #%1$d is already settled.";
    public static final String MESSAGE_NO_TRANSACTIONS = "No transactions found for %1$s.";
    public static final String MESSAGE_INVALID_TRANSACTION_INDEX = "The transaction index provided is invalid.";

    private final Index personIndex;
    private final Index transactionIndex;

    /**
     * Creates a SettleTransactionCommand to settle the transaction at {@code transactionIndex}
     * for the person at {@code personIndex}.
     */
    public SettleTransactionCommand(Index personIndex, Index transactionIndex) {
        requireNonNull(personIndex);
        requireNonNull(transactionIndex);
        this.personIndex = personIndex;
        this.transactionIndex = transactionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(personIndex.getZeroBased());
        List<Transaction> transactions = person.getTransactions().stream()
                .sorted(Comparator.comparingDouble(Transaction::getCurrAmount).reversed())
                .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_TRANSACTIONS, person.getName()));
        }

        if (transactionIndex.getZeroBased() >= transactions.size()) {
            throw new CommandException(MESSAGE_INVALID_TRANSACTION_INDEX);
        }

        Transaction transaction = transactions.get(transactionIndex.getZeroBased());

        if (transaction.isSettled()) {
            throw new CommandException(String.format(MESSAGE_ALREADY_SETTLED, transactionIndex.getOneBased()));
        }

        transaction.settle();

        // Refresh both parties so their PersonCard balances update immediately.
        // The transaction is shared between debtor and creditor (same object), so
        // both already see the settled state — we just need to trigger UI re-renders.
        model.setPerson(person, person);
        Person other = person.equals(transaction.getDebtor())
                ? transaction.getCreditor() : transaction.getDebtor();
        lastShownList.stream()
                .filter(p -> p.equals(other))
                .findFirst()
                .ifPresent(otherInList -> model.setPerson(otherInList, otherInList));

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                transactionIndex.getOneBased(), Messages.format(person)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof SettleTransactionCommand)) {
            return false;
        }
        SettleTransactionCommand otherCommand = (SettleTransactionCommand) other;
        return personIndex.equals(otherCommand.personIndex)
                && transactionIndex.equals(otherCommand.transactionIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("transactionIndex", transactionIndex)
                .toString();
    }
}
