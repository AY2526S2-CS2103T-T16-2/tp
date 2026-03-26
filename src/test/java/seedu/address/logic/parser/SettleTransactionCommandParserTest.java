package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleTransactionCommand;

public class SettleTransactionCommandParserTest {

    private final SettleTransactionCommandParser parser = new SettleTransactionCommandParser();

    @Test
    public void parse_validArgs_returnsSettleCommand() {
        assertParseSuccess(parser, "1 t/2",
                new SettleTransactionCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2)));
    }

    @Test
    public void parse_missingTransactionPrefix_throwsParseException() {
        assertParseFailure(parser, "1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidPersonIndex_throwsParseException() {
        assertParseFailure(parser, "a t/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidTransactionIndex_throwsParseException() {
        assertParseFailure(parser, "1 t/a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPersonIndex_throwsParseException() {
        assertParseFailure(parser, "t/1",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE));
    }
}
