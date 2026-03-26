package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;

public class SettleTransactionCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private Transaction addTransactionTo(Person person, Person other) {
        Transaction tx = new Transaction(person, other, 50.0, 0.0, "test");
        person.appendTransaction(tx);
        other.appendTransaction(tx);
        return tx;
    }

    @Test
    public void execute_validIndexes_marksTransactionSettled() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person other = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Transaction tx = addTransactionTo(person, other);

        SettleTransactionCommand command = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        command.execute(model);

        assertTrue(tx.isSettled());
    }

    @Test
    public void execute_validIndexes_returnsSuccessMessage() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person other = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        addTransactionTo(person, other);

        SettleTransactionCommand command = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        CommandResult result = command.execute(model);

        String expectedMessage = String.format(SettleTransactionCommand.MESSAGE_SUCCESS, 1, Messages.format(person));
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_alreadySettled_throwsCommandException() throws Exception {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person other = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Transaction tx = addTransactionTo(person, other);
        tx.settle();

        SettleTransactionCommand command = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        String expectedMessage = String.format(SettleTransactionCommand.MESSAGE_ALREADY_SETTLED, 1);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        SettleTransactionCommand command = new SettleTransactionCommand(outOfBound, INDEX_FIRST_PERSON);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_noTransactions_throwsCommandException() {
        // Person at index 5 has no transactions in the typical address book
        Index personIndex = Index.fromOneBased(5);
        Person person = model.getFilteredPersonList().get(personIndex.getZeroBased());
        SettleTransactionCommand command = new SettleTransactionCommand(personIndex, INDEX_FIRST_PERSON);
        String expectedMessage = String.format(SettleTransactionCommand.MESSAGE_NO_TRANSACTIONS, person.getName());
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_invalidTransactionIndex_throwsCommandException() {
        Person person = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person other = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        addTransactionTo(person, other);

        Index outOfBound = Index.fromOneBased(99);
        SettleTransactionCommand command = new SettleTransactionCommand(INDEX_FIRST_PERSON, outOfBound);
        assertCommandFailure(command, model, SettleTransactionCommand.MESSAGE_INVALID_TRANSACTION_INDEX);
    }

    @Test
    public void equals() {
        SettleTransactionCommand command1 = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        SettleTransactionCommand command2 = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        SettleTransactionCommand diffPerson = new SettleTransactionCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1));
        SettleTransactionCommand diffTxn = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        // same object -> true
        assertTrue(command1.equals(command1));

        // same values -> true
        assertTrue(command1.equals(command2));

        // different person index -> false
        assertFalse(command1.equals(diffPerson));

        // different transaction index -> false
        assertFalse(command1.equals(diffTxn));

        // different type -> false
        assertFalse(command1.equals(1));

        // null -> false
        assertFalse(command1.equals(null));
    }

    @Test
    public void toStringMethod() {
        SettleTransactionCommand command = new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));
        String expected = SettleTransactionCommand.class.getCanonicalName()
                + "{personIndex=" + INDEX_FIRST_PERSON + ", transactionIndex=" + Index.fromOneBased(2) + "}";
        assertEquals(expected, command.toString());
    }
}
