[![Java CI](https://github.com/AY2526S2-CS2103T-T16-2/tp/actions/workflows/gradle.yml/badge.svg?branch=master)](https://github.com/AY2526S2-CS2103T-T16-2/tp/actions/workflows/gradle.yml)
![Ui](docs/images/Ui.png)

# IOU – Simple Debt Tracking for Friends and Colleagues

# About IOU

## What is IOU?
IOU is a desktop application designed to help you keep track of small debts and loans between friends, colleagues, or roommates. Prefer typing over clicking? You’ll feel right at home here. Commands let you work fast, while the interface keeps everything visible and organized.

By using simple text commands instead of spreadsheets or mobile apps, IOU allows users to log financial transactions directly from their laptops in seconds. It’s ideal for staying on top of peer-to-peer debts during workdays, social events, or shared living situations.

## Why is this app needed?
Managing multiple small debts can be tedious and error-prone, especially when relying on spreadsheets or phone apps. These tools can be slow, disruptive, or hard to reference quickly.

IOU provides a keyboard-friendly, streamlined solution that lets users record, check, and update financial records instantly—saving time, reducing mistakes, and keeping your personal finances organized.

## Who are the target users?
IOU is especially suited for people who:
* Spend most of their day on a laptop or desktop
* Handle multiple informal loans or shared expenses
* Prefer typing commands over navigating menus or clicking buttons
* Want fast, clear, and organized access to personal finance records

## What value does IOU provide?
With IOU, users can:
* Quickly record new debts and loans
* Track outstanding balances at a glance
* Keep financial records accurate and up-to-date
* Replace cluttered spreadsheets with a smooth, efficient workflow

---

## Features

### Add Person

The **Add Person** feature allows users to create a profile for a friend or colleague so that debts and loans can be associated with them.

Command format:
add n/NAME [p/PHONE] [e/EMAIL]

Example:
add n/Alex Lim p/91234567

The name must contain only alphanumeric characters and spaces and cannot be blank. Leading and trailing spaces are automatically removed, and multiple internal spaces are treated as a single space. The system checks for duplicate names in a case-insensitive manner to prevent confusion when recording debts. If a duplicate name already exists, the system will reject the command.

If the command is successful, the application will display the message:
“New person added: Alex Lim.”

Errors may occur if the name field is missing, blank, contains special characters, or if multiple name values are provided. Optional phone and email fields follow the standard validation rules from the base application.

---

### Add Debt (owe)

The **Add Debt** feature records money that the user owes to a specific person.

Command format:
`owe INDEX a/AMOUNT [d/DESCRIPTION]`

Example:
`owe 1 a/12.50 d/Dinner at Fish Market`

The index refers to the person’s position in the current list displayed in the application. It must be a positive integer and must correspond to a valid person in the list. The amount must be a positive decimal value greater than zero and may contain up to two decimal places. Currency symbols are not allowed.

Multiple debts to the same person are allowed because users may owe money for different occasions such as meals, shared transport, or tickets.

If successful, the application displays
```
“Added debt of $12.50 to Alex Lim.”
```

Possible errors include invalid indices, missing prefixes such as a/, negative or zero amounts, currency symbols in the amount field, or values exceeding two decimal places.

---

### Add Loan (lent)

The **Add Loan** feature records money that another person owes to the user.

Command format:
`lent INDEX a/AMOUNT [d/DESCRIPTION]`

Example:
`lent 2 a/50 d/Borrowed for concert tickets`

The validation rules for this command are identical to the **owe** command. The index must refer to a valid person in the list, and the amount must be a positive number with up to two decimal places.

Multiple loan entries for the same person are allowed to capture separate transactions.

If successful, the system displays:
```
“Recorded loan of $50.00 to Sarah Tan.”
```

Errors may occur if the index is invalid, if the amount is negative or zero, if the command syntax is incorrect, or if required prefixes are missing.

---

### List Outstanding

The **List Outstanding** command displays only the people who currently have non-zero balances, meaning either you owe them money or they owe you money.

Command format:
`list`

This command helps reduce clutter in the interface by hiding individuals whose balances are already settled.

If the command is successful, the system displays:
```
“Listed all people with outstanding balances.”
```

If no outstanding balances exist, the system will show the message:
```
“No outstanding balances found. You're all caught up.”
```

An error will occur if additional parameters are provided, since the command does not accept arguments.

---

### Settle Transaction

The **Settle Transaction** feature allows users to mark a specific debt or loan as settled without deleting the record from the system. This preserves transaction history while ensuring the balance is updated.

Command format:
`settle PERSON_INDEX t/TRANSACTION_INDEX`

Example:
`settle 1 t/2`

The person index identifies the individual in the main list, while the transaction index refers to the specific transaction within that person's transaction history.

Once a transaction is settled, its status is updated in the interface and the person’s overall balance is recalculated.

Errors may occur if either index is invalid, if the transaction does not exist, if the command format is incorrect, or if the user attempts to settle a transaction that has already been settled.

---

### Delete Entry

The **Delete Entry** command allows users to remove either a person or a specific transaction from the records.

To delete a person:
`delete INDEX`

Example:
`delete 3`

To delete a transaction belonging to a person:
`delete INDEX t/TRANS_INDEX`

Example:
`delete 1 t/2`

Indices must always be positive integers that correspond to the positions shown in the current list view. When the list is filtered, the index refers to the visible position rather than the internal database ID.

If successful, the system displays messages such as:
```
“Deleted Alex Lim.”
```
or
```
“Deleted Transaction #2.”
```

Errors may occur if indices are out of range, non-numeric values are provided, or if the specified transaction does not exist.

---

### Auto-Save

IOU automatically saves all changes to ensure that no data is lost if the application closes unexpectedly. The auto-save function is triggered after any command that modifies the data, such as adding a debt, recording a loan, settling a transaction, or deleting an entry.

Data is stored in the file:
data/iou.json

If the system detects a corrupted data file when loading, it will display the message:
“Data file corrupted. Starting with an empty list.”

If the application encounters an input/output error while saving, it will display:
“ERROR: Unable to save data.”

---

## Command Summary

add – Add a new person to the system
owe – Record money that you owe someone
lent – Record money that someone owes you
list – Display people with outstanding balances
settle – Mark a transaction as settled
delete – Remove a person or transaction from the records

---

## Example Workflow

A typical workflow may look like this:
```
add n/Alex Lim
add n/Sarah Tan

owe 1 a/12.50 d/Lunch
lent 2 a/30 d/Movie tickets

list

settle 1 t/1
```

This sequence adds two people, records a debt and a loan, displays outstanding balances, and then settles one transaction.

---

## Project Information

IOU is a command-driven financial tracking application developed as part of a software engineering project. The system focuses on simplicity and speed, allowing users to record transactions quickly while maintaining a clear overview of outstanding balances.
