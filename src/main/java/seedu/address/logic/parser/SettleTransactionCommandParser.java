package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SettleTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SettleTransactionCommand object.
 */
public class SettleTransactionCommandParser implements Parser<SettleTransactionCommand> {

    private static final Prefix PREFIX_TRANSACTION_INDEX = new Prefix("t/");

    /**
     * Parses the given {@code String} of arguments in the context of the SettleTransactionCommand
     * and returns a SettleTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SettleTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRANSACTION_INDEX);

        if (!argMultimap.getValue(PREFIX_TRANSACTION_INDEX).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE));
        }

        try {
            Index personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            Index transactionIndex = ParserUtil.parseIndex(
                    argMultimap.getValue(PREFIX_TRANSACTION_INDEX).get());
            return new SettleTransactionCommand(personIndex, transactionIndex);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleTransactionCommand.MESSAGE_USAGE), pe);
        }
    }
}
