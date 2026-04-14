package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class MessagesTest {

    @Test
    public void format_emptyTagsAndTransactions_showsNonePlaceholders() {
        Person person = new Person(
                new Name("John Doe"),
                new Phone("98765432"),
                new Email("johnd@example.com"),
                new Address("John street, block 123, #01-01"),
                new HashSet<>());

        String formatted = Messages.format(person);
        assertTrue(formatted.contains("Tags: (NONE)"));
        assertTrue(formatted.contains("Transactions: (NONE)"));
    }
}

