package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class UiMessagesTest {

    @Test
    public void success_formatsPrefix() {
        assertEquals("[SUCCESS] hello", UiMessages.success("hello"));
    }

    @Test
    public void success_null_throws() {
        assertThrows(NullPointerException.class, () -> UiMessages.success(null));
    }

    @Test
    public void error_formatsPrefix() {
        assertEquals("[ERROR] bad", UiMessages.error("bad"));
    }

    @Test
    public void error_null_throws() {
        assertThrows(NullPointerException.class, () -> UiMessages.error(null));
    }

    @Test
    public void activeContactsTitle_formatsCount() {
        assertEquals("Active Contacts (0)", UiMessages.activeContactsTitle(0));
        assertEquals("Active Contacts (4)", UiMessages.activeContactsTitle(4));
    }

    @Test
    public void activeContactsTitle_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> UiMessages.activeContactsTitle(-1));
    }

    @Test
    public void transactionsTitle_formats() {
        assertEquals("Transactions (2) - Alex Yeoh", UiMessages.transactionsTitle(2, "Alex Yeoh"));
    }

    @Test
    public void transactionsTitle_nullName_throws() {
        assertThrows(NullPointerException.class, () -> UiMessages.transactionsTitle(1, null));
    }

    @Test
    public void transactionsTitle_negativeCount_throws() {
        assertThrows(IllegalArgumentException.class, () -> UiMessages.transactionsTitle(-1, "Alex"));
    }
}

