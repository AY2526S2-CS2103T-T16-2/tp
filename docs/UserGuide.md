---
layout: page
title: User Guide
---

# About IOU

## What is IOU?
IOU is a desktop application designed to help you keep track of small debts and loans between friends, colleagues, or roommates. Prefer typing over clicking? You'll feel right at home here. Commands let you work fast, while the interface keeps everything visible and organised.

By using simple text commands instead of spreadsheets or mobile apps, IOU allows users to log financial transactions directly from their laptops in seconds. It's ideal for staying on top of peer-to-peer debts during workdays, social events, or shared living situations.

## Why is this app needed?
Managing multiple small debts can be tedious and error-prone, especially when relying on spreadsheets or phone apps. These tools can be slow, disruptive, or hard to reference quickly.

IOU provides a keyboard-friendly, streamlined solution that lets users record, check, and update financial records instantly — saving time, reducing mistakes, and keeping your personal finances organised.

## Who are the target users?
IOU is especially suited for people who:
* Spend most of their day on a laptop or desktop
* Handle multiple informal loans or shared expenses
* Prefer typing commands over navigating menus or clicking buttons
* Want fast, clear, and organised access to personal finance records

## What value does IOU provide?
With IOU, users can:
* Quickly record new debts and loans
* Track outstanding balances at a glance
* Keep financial records accurate and up-to-date
* Replace cluttered spreadsheets with a smooth, efficient workflow


* Table of Contents
  {:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick Start

1. Ensure you have Java `17` or above installed on your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from the [Releases page](https://github.com/AY2526S2-CS2103T-T16-2/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for IOU.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar iou.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   If no person named `Me` exists yet, IOU inserts a default `Me` person at the top of the list on startup.<br>

   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * `list` — Lists all persons.
    * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` — Adds a person named `John Doe`.
    * `delete 3` — Deletes the 3rd person shown in the current list.
    * `clear` — Clears all entries except the `Me` contact.
    * `exit` — Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friends`, `t/friends t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, and `exit`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Select a person in the person list to view that person's transactions in the transaction panel.

* For commands that target a transaction using `t/TRANSACTION_INDEX`, the transaction index refers to the order shown in the transaction panel, sorted from the largest current amount to the smallest.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Parameter Summary

| Parameter | Prefix | Constraints          | Example |
|-----------|--------|----------------------|---------|
| Name | `n/` | Alphanumeric characters, spaces, hyphens (`-`), and apostrophes (`'`); must not be blank | `n/John Doe` or `n/O'Brien` |
| Phone | `p/` | Contains only numbers; At least 3 digits long | `p/98765432` |
| Email | `e/` | Standard `local-part@domain` format with at least one `.` after `@` | `e/johnd@example.com` |
| Address | `a/` | Any non-blank value  | `a/Blk 30, #06-40` |
| Tag | `t/` | Alphanumeric characters only (e.g. `friends`, `owesMoney`) | `t/friends` |
| Amount | `a/` | Positive number ≥ 0.01; at most 2 decimal places; no currency symbols or trailing `.` | `a/50` or `a/12.50` |
| Description | `d/` | Any non-blank value; used in `addtxn` and as a filter prefix in `find` | `d/lunch` |
| Min Amount | `min/` | Positive number; used only in `find` | `min/50` |
| Max Amount | `max/` | Positive number; must be ≥ `min/` if both supplied; used only in `find` | `max/100` |

--------------------------------------------------------------------------------------------------------------------

### View help : `help`

Shows a message with a link to this User Guide.

Format: `help`

Expected output:

```
Opened help window.
```

![help message](images/helpMessage.png)

--------------------------------------------------------------------------------------------------------------------

### Add a person : `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

* All fields except `TAG` are required.
* **Name** accepts alphanumeric characters, spaces, hyphens, and apostrophes (e.g. `O'Brien`, `Jean-Paul`).
* **Phone** must be exactly 8 digits and start with `8` or `9` (Singapore format).
* **Email** must follow the `local-part@domain` format with at least one `.` after `@`.
* **Address** can be any non-blank value.
* **Tags** must be alphanumeric only (e.g. `friends`, `owesMoney`). A person can have any number of tags, including zero.
* IOU does not allow duplicate persons. Two persons are considered duplicates if they have the same name **or** the same phone + email + address.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning:**
If you attempt to add a duplicate person, IOU will show: `This person already exists in IOU.`
</div>

Examples:

| Command | What it does |
|---------|-------------|
| `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` | Adds John Doe with no tags |
| `add n/Betsy Crowe e/betsycrowe@example.com a/Newgate Prison p/92345678 t/friends t/owesMoney` | Adds Betsy Crowe with two tags |

Expected output (on success):

```
New person added: John Doe; Phone: 98765432; Email: johnd@example.com; Address: John street, block 123, #01-01; Tags: []
```

Expected output (on failure):

```
This person already exists in IOU.
```

--------------------------------------------------------------------------------------------------------------------

### List all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use `list` after a `find` command to return to the full person list.
</div>

Expected output:

```
Listed all persons
```

--------------------------------------------------------------------------------------------------------------------

### Edit a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list and **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, existing tags are **replaced entirely** — adding tags is not cumulative. Use `t/` without any value to clear all tags.
* The **name of the `Me` contact cannot be edited**. Other fields of `Me` (phone, email, address) can be changed freely.
* Editing is rejected if it would create a duplicate of another existing person.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning:**
Editing tags replaces all existing tags. If a person has tags `friends` and `colleagues`, running `edit 1 t/family` results in only the `family` tag remaining. Use `t/` with no value to clear all tags.
</div>

Examples:

| Command | What it does |
|---------|-------------|
| `edit 1 p/91234567 e/johndoe@example.com` | Updates the phone and email of the 1st person |
| `edit 2 n/Betsy Crower t/` | Renames the 2nd person and clears all their tags |

Expected output (on success):

```
Edited Person: Betsy Crower; Phone: ...; Tags: []
```

Error messages:

| Error | Cause |
|-------|-------|
| `At least one field to edit must be provided.` | No fields were supplied |
| `This person already exists in the address book.` | Edit would create a duplicate |
| `The name of the 'me' contact cannot be edited.` | Attempted to rename the Me contact |

--------------------------------------------------------------------------------------------------------------------

### Delete a person or transaction : `delete`

Removes a specific person or transaction from the records.

**Person Deletion Format:** `delete INDEX`

**Transaction Deletion Format:** `delete INDEX t/TRANSACTION_INDEX`

* The person index refers to the index number shown in the displayed person list and **must be a positive integer**.
* The transaction index refers to the displayed transaction panel for the selected person (sorted largest to smallest by current amount).
* The `Me` contact **cannot be deleted**.
* Deletion is **permanent** and cannot be undone.

<div markdown="span" class="alert alert-warning">:exclamation: **Warning:**
Deleting a person removes them and all their transactions from every other person's panel. Consider using `settle` instead for paid transactions to preserve your financial history.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Deleting a transaction removes the shared record from both the debtor's and creditor's panels simultaneously.
</div>

Examples:

| Command | What it does |
|---------|-------------|
| `delete 2` | Deletes the 2nd person and all their transactions |
| `find Betsy` then `delete 1` | Deletes the 1st person in the filtered results |
| `delete 1 t/2` | Deletes transaction #2 from person 1's panel (and from the counterparty's panel) |

Expected output (on success — person):

```
Deleted Person: John Doe; Phone: 98765432; ...
```

Expected output (on success — transaction):

```
Deleted Transaction #2: $50.00 | lunch | Alice → Bob
```

Error messages:

| Error | Cause |
|-------|-------|
| `The 'Me' contact cannot be deleted.` | Attempted to delete the Me contact |
| `No transactions found for [Person].` | The selected person has no transactions |
| `The transaction index provided is invalid` | Transaction index is out of range |

<div markdown="span" class="alert alert-info">:information_source: **Note:**
Transaction indexes may shift after deletion. Always re-check the transaction panel before deleting multiple transactions.
</div>

--------------------------------------------------------------------------------------------------------------------

### Add a transaction : `addtxn`

Adds a transaction between two persons in the address book. The debtor owes the creditor the specified amount.

Format: `addtxn DEBTOR_INDEX CREDITOR_INDEX a/AMOUNT d/DESCRIPTION`

* Both indexes refer to the index numbers shown in the displayed person list and **must be positive integers**.
* Both indexes **must be different** — a person cannot transact with themselves.
* `AMOUNT` must be a positive number ≥ 0.01 with at most 2 decimal places. Currency symbols (e.g. `$`) and trailing decimals (e.g. `50.`) are not allowed.
* `DESCRIPTION` must not be empty.
* The transaction is a **shared record** — it appears in both persons' transaction panels.

Examples:

| Command | What it does |
|---------|-------------|
| `addtxn 1 2 a/50 d/lunch` | Records that person 1 owes person 2 $50.00 for lunch |
| `addtxn 3 1 a/200 d/Personal loan` | Records that person 3 owes person 1 $200.00 |
| `addtxn 4 1 a/12.50 d/Concert tickets` | Records that person 4 owes person 1 $12.50 |

Expected output (on success):

```
New transaction added (#3): Alice owes Bob - lunch
```

Error messages:

| Error | Cause |
|-------|-------|
| `Debtor and creditor cannot be the same person.` | Both indexes are identical |
| `The debtor index provided is invalid or out of range.` | Debtor index is out of range |
| `The creditor index provided is invalid or out of range.` | Creditor index is out of range |
| `Amount must be a positive number.` | Amount is 0, negative, or not a number |
| `Amount must use at most 2 decimal places and cannot end with a decimal point.` | Too many decimal places or trailing `.` |
| `Description cannot be empty.` | The `d/` prefix was missing or blank |

<div markdown="span" class="alert alert-info">:information_source: **Note:**
Modifying or settling a transaction from either person's view affects both sides, as the record is shared.
</div>

--------------------------------------------------------------------------------------------------------------------

### Advanced search : `find`

Filters the person list using one or more criteria. All provided filters use **AND** logic — a person must match every specified filter to appear in the results.

Format: `find [n/NAME] [d/DESCRIPTION] [min/MIN_AMOUNT] [max/MAX_AMOUNT] [t/TAG]` (at least one filter required)

* Search is **case-insensitive** for all text-based filters.
* `n/`, `d/`, and `t/` support **partial matching** (e.g. `n/alex` matches "Alex", "Alexander").
* `min/` and `max/` check whether **any** of the person's transactions satisfies the bound.
* `min/` and `max/` must be positive numbers; if both are given, `min/` must not exceed `max/`.

Filter types:

| Filter | Prefix | Behaviour |
|--------|--------|-----------|
| Name | `n/` | Partial, case-insensitive match against the person's full name |
| Description | `d/` | Partial, case-insensitive match against any of the person's transaction descriptions |
| Min Amount | `min/` | Person has at least one transaction with current amount ≥ specified value |
| Max Amount | `max/` | Person has at least one transaction with current amount ≤ specified value |
| Tag | `t/` | Partial, case-insensitive match against the person's tag names |

Examples:

| Command | What it finds |
|---------|--------------|
| `find n/john` | Anyone with "john" anywhere in their name |
| `find d/dinner` | Persons with any transaction containing "dinner" in its description |
| `find min/100` | Persons with at least one transaction ≥ $100 |
| `find max/20` | Persons with at least one transaction ≤ $20 |
| `find t/friend min/100` | Persons tagged "friend" (partial) with at least one transaction ≥ $100 |
| `find d/lunch min/10 max/30` | Persons with a lunch transaction between $10 and $30 |
| `find n/alex d/dinner` | Persons named "Alex" with any dinner transaction |

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Start broad with one filter, then add more to narrow results. Use `list` to return to the full person list.
</div>

Expected output:

```
2 persons listed!
```

Error messages:

| Error | Cause |
|-------|-------|
| `At least one filter must be provided.` | Called `find` with no parameters |
| `Minimum amount cannot be greater than maximum amount.` | `min/` value exceeds `max/` value |
| `Amount values for min/ and max/ must be positive numbers (e.g. 10 or 5.50).` | Invalid number entered |

<div markdown="span" class="alert alert-info">:information_source: **Note:**
After using `find`, displayed indices change to match the filtered view. Subsequent commands (`delete`, `settle`, `addtxn`) operate on these filtered indices, not the original positions.
</div>

--------------------------------------------------------------------------------------------------------------------

### Settle a transaction : `settle`

Marks a specific transaction as paid. The record remains visible in the transaction history with a `Settled` status and a $0.00 outstanding balance, preserving your financial history.

Format: `settle PERSON_INDEX t/TRANSACTION_INDEX`

* The person index refers to the displayed person list.
* The transaction index refers to the displayed transaction panel for that selected person (sorted by current amount, largest first).
* **Settled transactions cannot be unsettled** — the change is permanent.
* The success message shows the original amount, description, and both parties.
* Settling from either person's panel updates the shared transaction record for both sides.

Examples:

| Command | What it does |
|---------|-------------|
| `settle 1 t/2` | Marks transaction #2 in person 1's panel as settled |
| `settle 3 t/1` | Marks the largest outstanding transaction for person 3 as settled |

Expected output (on success):

```
Settled Transaction #2: $25.00 | lunch | Alice -> Bob
```

Error messages:

| Error | Cause |
|-------|-------|
| `This transaction has already been settled.` | Transaction was already settled |
| `No transactions found for [Person].` | The selected person has no transactions |
| `The transaction index provided is invalid` | Transaction index is out of range |

--------------------------------------------------------------------------------------------------------------------

### Simplify debts among a group : `simplify`

Computes a minimal settlement plan for 3 or more selected people and displays who should pay whom. This command is **preview-only** — no transactions are modified.

Format: `simplify PERSON_INDEX_1 PERSON_INDEX_2 PERSON_INDEX_3 [MORE_PERSON_INDEXES]...`

* At least **3 distinct** person indexes must be provided.
* All indexes refer to the currently displayed person list.
* Only **unsettled** in-group transactions (where both parties are in the selected group) are included.
* The plan is computed using a greedy net-balance matching algorithm that minimises the total number of transfers.

Examples:

| Command | What it does |
|---------|-------------|
| `simplify 1 2 3` | Computes a minimal transfer plan among persons 1, 2, and 3 |
| `simplify 1 2 3 4` | Computes a minimal transfer plan among persons 1 to 4 |

Expected output (transfers needed):

```
Simplified settlement plan (3 persons): Alice, Bob, Charlie
1. Charlie pays Alice $30.00
2. Bob pays Alice $10.00
```

Expected output (no transfers needed):

```
Simplified settlement plan (3 persons): Alice, Bob, Charlie
No payments needed among selected persons.
```

Error messages:

| Error | Cause |
|-------|-------|
| `At least 3 distinct person indices are required.` | Fewer than 3 indexes supplied |
| `Duplicate person index detected: N` | The same index was provided more than once |

--------------------------------------------------------------------------------------------------------------------

### Settle up a group : `settleup`

Marks all unsettled in-group transactions as settled in one action for 3 or more selected people.

Format: `settleup PERSON_INDEX_1 PERSON_INDEX_2 PERSON_INDEX_3 [MORE_PERSON_INDEXES]...`

* At least **3 distinct** person indexes must be provided.
* All indexes refer to the currently displayed person list.
* Only transactions where **both** the debtor and the creditor are within the selected group are settled. Transactions with persons outside the group are left unchanged.

Examples:

| Command | What it does |
|---------|-------------|
| `settleup 1 2 3` | Settles all in-group transactions among persons 1, 2, and 3 |
| `settleup 1 2 3 4 5` | Settles all in-group transactions among persons 1 to 5 |

Expected output (on success):

```
Settled up group (3 persons): Alice, Bob, Charlie
5 transaction(s) settled. Total amount: $320.00
```

Expected output (nothing to settle):

```
No unsettled transactions found among the selected group.
```

Error messages:

| Error | Cause |
|-------|-------|
| `At least 3 distinct person indices are required.` | Fewer than 3 indexes supplied |
| `Duplicate person index detected: N` | The same index was provided more than once |

--------------------------------------------------------------------------------------------------------------------

### Clear all entries : `clear`

Clears all persons and their transactions from the address book. The `Me` contact is always preserved.

Format: `clear`

<div markdown="span" class="alert alert-warning">:exclamation: **Warning:**
This deletes all persons and transactions and cannot be undone. The `Me` contact is retained, but all its transactions are removed.
</div>

Expected output:

```
[SUCCESS]Address book has been cleared!
```

--------------------------------------------------------------------------------------------------------------------

### Exit the program : `exit`

Exits the program.

Format: `exit`


--------------------------------------------------------------------------------------------------------------------

### Saving the data

IOU saves data to disk automatically after any command that changes the data. There is no need to save manually.

Data is stored in `[JAR file location]/data/addressbook.json`.

--------------------------------------------------------------------------------------------------------------------

### Editing the data file

Advanced users can edit the JSON data file directly at `[JAR file location]/data/addressbook.json`.

* Transactions are stored within each person's record. If you edit transactions manually, ensure the debtor and creditor fields still refer to valid persons in the file.
* The file must not contain duplicate persons (same name, or same phone + email + address).

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes make the data file invalid, IOU will discard it and start with an empty address book at the next run. Always take a backup before editing the file directly.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another computer?<br>
**A**: Install the app on the other computer and overwrite the empty data file it creates with the file that contains the data from your previous IOU home folder.

**Q**: Can I undo a settled transaction?<br>
**A**: No. Settled transactions cannot be unsettled. If it was settled by mistake, delete it with `delete INDEX t/TRANSACTION_INDEX` and re-enter it with `addtxn`.

**Q**: What happens when I use `clear`?<br>
**A**: All persons and their transactions are deleted, but the `Me` contact is always preserved (with its transactions removed).

--------------------------------------------------------------------------------------------------------------------

## Known Issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file in the home folder before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command Summary

| Action | Format | Example |
|--------|--------|---------|
| **Help** | `help` | `help` |
| **Add** | `add n/NAME p/PHONE e/EMAIL a/ADDRESS [t/TAG]…​` | `add n/James Ho p/92224444 e/jamesho@example.com a/123, Clementi Rd t/friends` |
| **List** | `list` | `list` |
| **Edit** | `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​` | `edit 2 n/James Lee e/jameslee@example.com` |
| **Find** | `find [n/NAME] [d/DESC] [min/MIN] [max/MAX] [t/TAG]` | `find n/James` or `find d/lunch min/10 max/30` |
| **Delete Person** | `delete INDEX` | `delete 3` |
| **Delete Transaction** | `delete INDEX t/TRANSACTION_INDEX` | `delete 3 t/1` |
| **Add Transaction** | `addtxn DEBTOR_INDEX CREDITOR_INDEX a/AMOUNT d/DESCRIPTION` | `addtxn 1 2 a/12.50 d/Dinner at Fish Market` |
| **Settle** | `settle PERSON_INDEX t/TRANSACTION_INDEX` | `settle 1 t/2` |
| **Simplify** | `simplify INDEX INDEX INDEX [MORE]...` | `simplify 1 2 3 4` |
| **Settle Up** | `settleup INDEX INDEX INDEX [MORE]...` | `settleup 1 2 3 4` |
| **Clear** | `clear` | `clear` |
| **Exit** | `exit` | `exit` |
