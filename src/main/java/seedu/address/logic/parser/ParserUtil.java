package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.TransactionDescriptor;
import seedu.address.model.transaction.TransactionDescriptor.CompoundingType;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    public static final String MESSAGE_INVALID_TRANSACTION =
            "Transaction details should be in the form '[m|y ] amount, rate, description',"
                    + " where amount is positive and rate is between 0 and 100 "
                    + "e.g. '10, 5, lunch' or 'm 10, 5, lunch'";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String transactionDetails} into a {@code TransactionDescriptor}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * <p>Expected format: {@code [m|y ]amount, rate, description}
     * e.g. {@code 10, 5, lunch} or {@code m 10, 5, lunch}
     *
     * <p>The debtor and creditor are NOT part of the transaction prefix value —
     * they are passed as separate index arguments to {@code AddTransactionCommandParser}.
     *
     * @throws ParseException if the given {@code transactionDetails} is invalid.
     */
    public static TransactionDescriptor parseTransactionDescriptor(String transactionDetails) throws ParseException {
        requireNonNull(transactionDetails);
        String trimmed = transactionDetails.trim();
        String lowercased = trimmed.toLowerCase();

        CompoundingType compoundingType = CompoundingType.NONE;
        String withoutType = trimmed;

        if (lowercased.startsWith("m ")) {
            compoundingType = CompoundingType.MONTHLY;
            withoutType = trimmed.substring(2);
        } else if (lowercased.startsWith("y ")) {
            compoundingType = CompoundingType.YEARLY;
            withoutType = trimmed.substring(2);
        }

        // Expected format after stripping type: amount, rate, description
        String[] parts = withoutType.split("\\s*,\\s*", 3);

        if (parts.length != 3) {
            throw new ParseException(MESSAGE_INVALID_TRANSACTION);
        }

        try {
            double amount = Double.parseDouble(parts[0].trim());
            double rate = Double.parseDouble(parts[1].trim());
            String description = parts[2].trim();

            if (amount < 0 || rate < 0 || rate > 100 || description.isEmpty()) {
                throw new ParseException(MESSAGE_INVALID_TRANSACTION);
            }

            return new TransactionDescriptor(compoundingType, amount, rate, description);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_TRANSACTION);
        }
    }

    /**
     * Parses a {@code String amount} into an {@code Amount}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code amount} is invalid.
     */
    public static Amount parseAmount(String amount) throws ParseException {
        requireNonNull(amount);
        String trimmedAmount = amount.trim();
        if (!Amount.isValidAmount(trimmedAmount)) {
            throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
        }
        return new Amount(trimmedAmount);
    }
}
