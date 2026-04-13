package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ME;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
        assertThrows(UnsupportedOperationException.class, () -> person.getTransactions().clear());
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ME.isSamePerson(ME));

        // null -> returns false
        assertFalse(ME.isSamePerson(null));

        // same identity fields, different tags -> returns true (name matches)
        Person editedAlice = new PersonBuilder(ME).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ME.isSamePerson(editedAlice));

        // different name but same phone, email, address -> returns true (contact details match)
        editedAlice = new PersonBuilder(ME).withName(VALID_NAME_BOB).build();
        assertTrue(ME.isSamePerson(editedAlice));

        // different name and phone only -> returns false (phone+email+address no longer all match)
        editedAlice = new PersonBuilder(ME).withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ME.isSamePerson(editedAlice));

        // different name and email only -> returns false (phone+email+address no longer all match)
        editedAlice = new PersonBuilder(ME).withName(VALID_NAME_BOB).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ME.isSamePerson(editedAlice));

        // different name and address only -> returns false (phone+email+address no longer all match)
        editedAlice = new PersonBuilder(ME).withName(VALID_NAME_BOB).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ME.isSamePerson(editedAlice));

        // name differs in case only -> returns true (normalised name comparison is case-insensitive)
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces only -> returns true (normaliseName strips trailing spaces)
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // completely different name and different contact details -> returns false
        editedAlice = new PersonBuilder(ME)
                .withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB)
                .build();
        assertFalse(ME.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ME).build();
        assertTrue(ME.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ME.equals(ME));

        // null -> returns false
        assertFalse(ME.equals(null));

        // different type -> returns false
        assertFalse(ME.equals(5));

        // different person -> returns false
        assertFalse(ME.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ME).withName(VALID_NAME_BOB).build();
        assertFalse(ME.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ME).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ME.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ME).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ME.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ME).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ME.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ME).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ME.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ME.getName()
                + ", phone=" + ME.getPhone()
                + ", email=" + ME.getEmail()
                + ", address=" + ME.getAddress()
                + ", tags=" + ME.getTags()
                + ", transactions=" + ME.getTransactions()
                + "}";
        assertEquals(expected, ME.toString());
    }
}
