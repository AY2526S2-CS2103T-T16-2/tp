package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRANSACTION;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTransactionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.transaction.TransactionDescriptor;

/**
 * Parses input arguments and creates a new AddTransactionCommand object.
 *
 * <p>Expected format:
 * {@code addtxn DEBTOR_INDEX CREDITOR_INDEX t/[m|y ]amount, rate, description}
 * e.g. {@code addtxn 2 3 t/10, 5, lunch}
 *
 * <p>The debtor and creditor indices refer to the one-based positions of persons
 * currently displayed in the person list. The transaction prefix optionally starts
 * with "m " (monthly) or "y " (yearly) to indicate the compounding type.
 */
public class AddTransactionCommandParser implements Parser<AddTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddTransactionCommand
     * and returns an AddTransactionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddTransactionCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TRANSACTION);

        // Preamble must contain exactly "DEBTOR_INDEX CREDITOR_INDEX"
        String preamble = argMultimap.getPreamble().trim();
        String[] indices = preamble.split("\\s+", 2);

        if (indices.length != 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));
        }

        Index debtorIndex;
        Index creditorIndex;

        try {
            debtorIndex = ParserUtil.parseIndex(indices[0]);
            creditorIndex = ParserUtil.parseIndex(indices[1]);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_TRANSACTION).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTransactionCommand.MESSAGE_USAGE));
        }

        TransactionDescriptor descriptor =
                ParserUtil.parseTransactionDescriptor(argMultimap.getValue(PREFIX_TRANSACTION).get());

        return new AddTransactionCommand(debtorIndex, creditorIndex, descriptor);
    }
}
