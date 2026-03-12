package seedu.address.storage;

import static seedu.address.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionType;
import seedu.address.testutil.TransactionBuilder;

public class JsonAdaptedTransactionTest {

    private static final String INVALID_TYPE = "invalid";
    private static final Double INVALID_AMOUNT = -10.0;

    private static final Transaction VALID_TRANSACTION = new TransactionBuilder()
            .withAmount(10.0)
            .withDescription("Lunch")
            .build();

    @Test
    public void toModelType_validTransaction_returnsTransaction() throws Exception {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_TRANSACTION);
        assertEquals(VALID_TRANSACTION, transaction.toModelType());
    }

    @Test
    public void toModelType_invalidType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(INVALID_TYPE, VALID_TRANSACTION.getAmount(),
                VALID_TRANSACTION.getDescription());
        assertThrows(IllegalValueException.class, TransactionType.MESSAGE_CONSTRAINTS, transaction::toModelType);
    }

    @Test
    public void toModelType_nullType_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(null, VALID_TRANSACTION.getAmount(),
                VALID_TRANSACTION.getDescription());
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, "type");
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_TRANSACTION.getType().name(),
                INVALID_AMOUNT, VALID_TRANSACTION.getDescription());
        assertThrows(IllegalValueException.class, Transaction.MESSAGE_CONSTRAINTS, transaction::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_TRANSACTION.getType().name(),
                null, VALID_TRANSACTION.getDescription());
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, "amount");
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }

    @Test
    public void toModelType_nullDescription_throwsIllegalValueException() {
        JsonAdaptedTransaction transaction = new JsonAdaptedTransaction(VALID_TRANSACTION.getType().name(),
                VALID_TRANSACTION.getAmount(), null);
        String expectedMessage = String.format(JsonAdaptedTransaction.MISSING_FIELD_MESSAGE_FORMAT, "description");
        assertThrows(IllegalValueException.class, expectedMessage, transaction::toModelType);
    }
}
