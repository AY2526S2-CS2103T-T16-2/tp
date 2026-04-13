package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.util.SampleDataUtil;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        // No Me contact exists in the address book, so the default getMeContact() is used
        Model model = new ModelManager();
        AddressBook clearedAddressBook = new AddressBook();
        clearedAddressBook.addPersonAtFront(SampleDataUtil.getMeContact());
        Model expectedModel = new ModelManager(clearedAddressBook, new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        // The Me contact already exists in the typical address book and should be preserved
        // (with its transactions cleared) while all other contacts are removed
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        Person existingMe = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(SampleDataUtil.ME_NAME))
                .findFirst()
                .orElseGet(SampleDataUtil::getMeContact);
        Person meWithoutTransactions = new Person(
                existingMe.getName(),
                existingMe.getPhone(),
                existingMe.getEmail(),
                existingMe.getAddress(),
                existingMe.getTags(),
                Set.of());

        AddressBook clearedAddressBook = new AddressBook();
        clearedAddressBook.addPersonAtFront(meWithoutTransactions);
        Model expectedModel = new ModelManager(clearedAddressBook, new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBookWithoutMeContact_success() {
        // If no Me contact exists in a non-empty address book, fall back to the default
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        // Remove the Me contact if present so this simulates the fallback path
        model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(SampleDataUtil.ME_NAME))
                .findFirst()
                .ifPresent(model::deletePerson);

        AddressBook clearedAddressBook = new AddressBook();
        clearedAddressBook.addPersonAtFront(SampleDataUtil.getMeContact());
        Model expectedModel = new ModelManager(clearedAddressBook, new UserPrefs());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
