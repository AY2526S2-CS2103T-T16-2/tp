package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

/**
 * Deletes a person or that person's transaction identified using its displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) [t/TRANS_INDEX]\n"
            + "Example: " + COMMAND_WORD + " 1 t/2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s";
    public static final String MESSAGE_DELETE_TRANSACTION_SUCCESS = "Deleted Transaction #%1$d.";

    private final Index targetIndex;
    private final Index transactionIndex;

    public DeleteCommand(Index targetIndex) {
        this(targetIndex, null);
    }

    public DeleteCommand(Index targetIndex, Index transactionIndex) {
        this.targetIndex = targetIndex;
        this.transactionIndex = transactionIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        if (transactionIndex == null) {
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete)));
        }

        List<Transaction> transactions = personToDelete.getTransactions();
        if (transactions.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_TRANSACTIONS);
        }
        if (transactionIndex.getZeroBased() >= transactions.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TRANSACTION_DISPLAYED_INDEX);
        }

        model.deleteTransaction(personToDelete, transactionIndex);
        return new CommandResult(String.format(MESSAGE_DELETE_TRANSACTION_SUCCESS, transactionIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex)
                && Objects.equals(transactionIndex, otherDeleteCommand.transactionIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("transactionIndex", transactionIndex)
                .toString();
    }
}
