package seedu.address.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmountTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amount(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        // empty string
        assertThrows(IllegalArgumentException.class, () -> new Amount(""));
        // zero
        assertThrows(IllegalArgumentException.class, () -> new Amount("0"));
        // negative
        assertThrows(IllegalArgumentException.class, () -> new Amount("-5"));
        // currency symbol
        assertThrows(IllegalArgumentException.class, () -> new Amount("$5.00"));
        // too many decimal places
        assertThrows(IllegalArgumentException.class, () -> new Amount("5.123"));
    }

    @Test
    public void isValidAmount() {
        // null
        assertFalse(Amount.isValidAmount(null));

        // invalid amounts
        assertFalse(Amount.isValidAmount("")); // empty string
        assertFalse(Amount.isValidAmount(" ")); // spaces only
        assertFalse(Amount.isValidAmount("0")); // zero not allowed
        assertFalse(Amount.isValidAmount("0.00")); // zero with decimals
        assertFalse(Amount.isValidAmount("-5")); // negative
        assertFalse(Amount.isValidAmount("-5.00")); // negative with decimals
        assertFalse(Amount.isValidAmount("$5.00")); // currency symbol
        assertFalse(Amount.isValidAmount("5.123")); // too many decimal places
        assertFalse(Amount.isValidAmount("five")); // non-numeric
        assertFalse(Amount.isValidAmount("5 .00")); // spaces within

        // valid amounts
        assertTrue(Amount.isValidAmount("1")); // whole number
        assertTrue(Amount.isValidAmount("5.5")); // one decimal place
        assertTrue(Amount.isValidAmount("12.50")); // two decimal places
        assertTrue(Amount.isValidAmount("100")); // larger whole number
        assertTrue(Amount.isValidAmount("9999.99")); // large amount
        assertTrue(Amount.isValidAmount("0.01")); // smallest valid amount
    }

    @Test
    public void toString_formatsAsCurrency() {
        assertEquals("$12.50", new Amount("12.5").toString());
        assertEquals("$1.00", new Amount("1").toString());
        assertEquals("$0.01", new Amount("0.01").toString());
        assertEquals("$100.00", new Amount("100").toString());
    }

    @Test
    public void equals() {
        Amount amount = new Amount("12.50");

        // same value -> returns true
        assertTrue(amount.equals(new Amount("12.50")));
        assertTrue(amount.equals(new Amount("12.5"))); // equivalent decimal

        // same object -> returns true
        assertTrue(amount.equals(amount));

        // null -> returns false
        assertFalse(amount.equals(null));

        // different type -> returns false
        assertFalse(amount.equals("12.50"));

        // different value -> returns false
        assertFalse(amount.equals(new Amount("5.00")));
    }

    @Test
    public void hashCode_consistentWithEquals() {
        Amount a1 = new Amount("12.50");
        Amount a2 = new Amount("12.5");
        assertEquals(a1.hashCode(), a2.hashCode());
    }

}