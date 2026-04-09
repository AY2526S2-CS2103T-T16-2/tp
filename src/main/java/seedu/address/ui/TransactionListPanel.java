package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;
import seedu.address.model.transaction.Transaction;
import seedu.address.model.transaction.TransactionComparators;
import seedu.address.model.transaction.SortDirection;
import seedu.address.model.transaction.TransactionDisplayHelper;
import seedu.address.model.transaction.TransactionSortKey;
import seedu.address.model.transaction.TransactionSortState;

/**
 * Panel that displays transactions for the currently selected person.
 */
public class TransactionListPanel extends UiPart<Region> {

    private static final String FXML = "TransactionListPanel.fxml";

    private static final String NO_SELECTION_TITLE = "Transactions - Select a person";
    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_SETTLED = "Settled";
    private static final String DIRECTION_OWE = "Owe";
    private static final String DIRECTION_LENT = "Lent";
    private static final String TYPE_OWE = "Owe";
    private static final String TYPE_LENT = "Lent";
    private static final String STYLE_TX_OWE = "tx-type-owe";
    private static final String STYLE_TX_LENT = "tx-type-lent";
    private static final String STYLE_ROW_SETTLED = "tx-row-settled";
    private static final String STYLE_STATUS_SETTLED = "tx-status-settled";

    static final class DisplayModel {
        private final String title;
        private final List<Transaction> transactions;

        private DisplayModel(String title, List<Transaction> transactions) {
            this.title = title;
            this.transactions = transactions;
        }

        String getTitle() {
            return title;
        }

        List<Transaction> getTransactions() {
            return transactions;
        }
    }

    static final class TypeCellModel {
        private final String text;
        private final String styleClass;

        private TypeCellModel(String text, String styleClass) {
            this.text = text;
            this.styleClass = styleClass;
        }

        String getText() {
            return text;
        }

        String getStyleClass() {
            return styleClass;
        }
    }

    @FXML
    private Label title;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Number> indexColumn;

    @FXML
    private TableColumn<Transaction, String> directionColumn;

    @FXML
    private TableColumn<Transaction, String> otherPartyColumn;

    @FXML
    private TableColumn<Transaction, Double> amountColumn;

    @FXML
    private TableColumn<Transaction, String> descriptionColumn;

    @FXML
    private TableColumn<Transaction, String> statusColumn;

    @FXML
    private TableColumn<Transaction, String> dateColumn;

    private Person currentPerson;
    private TransactionSortState currentSortState = TransactionSortState.defaultState();
    private Consumer<TransactionSortState> sortStateListener = state -> {};

    /**
     * Creates a transaction list panel showing no selection initially.
     */
    public TransactionListPanel() {
        super(FXML);
        showNoSelection();
    }

    @FXML
    private void initialize() {
        transactionTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        transactionTable.setRowFactory(table -> new TransactionRow());

        indexColumn.setSortable(false);
        indexColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setText(null);
                    return;
                }

                setText(Integer.toString(getIndex() + 1));
            }
        });

        directionColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(directionText(cellData.getValue())));
        directionColumn.setCellFactory(col -> new DirectionCell());
        directionColumn.setComparator(String.CASE_INSENSITIVE_ORDER);

        otherPartyColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(otherPartyName(cellData.getValue())));
        otherPartyColumn.setComparator(String.CASE_INSENSITIVE_ORDER);

        amountColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(Math.abs(cellData.getValue().getCurrAmount())));
        amountColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setText(null);
                    return;
                }

                Transaction transaction = getTableRow() == null ? null : getTableRow().getItem();
                setText(transaction == null ? null : amountText(transaction.getOriginalAmount()));
            }
        });
        amountColumn.setComparator(Comparator.nullsLast(Comparator.naturalOrder()));

        descriptionColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(descriptionText(cellData.getValue())));
        descriptionColumn.setComparator(String.CASE_INSENSITIVE_ORDER);

        statusColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(statusText(cellData.getValue())));
        statusColumn.setCellFactory(col -> new StatusCell());
        statusColumn.setComparator(String.CASE_INSENSITIVE_ORDER);

        dateColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(dateText(cellData.getValue())));
        dateColumn.setComparator(String.CASE_INSENSITIVE_ORDER);

        amountColumn.setSortType(TableColumn.SortType.DESCENDING);
        transactionTable.getSortOrder().setAll(amountColumn);
        transactionTable.getSortOrder().addListener((ListChangeListener<TableColumn<Transaction, ?>>) change ->
                notifySortStateChanged());
        notifySortStateChanged();
    }

    /**
     * Displays the transactions belonging to the given person.
     *
     * @param person The selected person. If null, the panel resets to the no-selection state.
     */
    public void displayPerson(Person person) {
        this.currentPerson = person;
        DisplayModel model = displayModelFor(person);
        title.setText(model.getTitle());
        transactionTable.setItems(FXCollections.observableArrayList(model.getTransactions()));
        transactionTable.sort();
        transactionTable.refresh();
        notifySortStateChanged();
    }

    private void showNoSelection() {
        DisplayModel model = displayModelFor(null);
        title.setText(model.getTitle());
        transactionTable.setItems(FXCollections.observableArrayList(model.getTransactions()));
        transactionTable.sort();
        transactionTable.refresh();
        notifySortStateChanged();
    }

    /**
     * Registers a listener that is notified whenever the sort order changes.
     */
    public void setSortChangeListener(Consumer<TransactionSortState> listener) {
        Objects.requireNonNull(listener);
        sortStateListener = listener;
        sortStateListener.accept(currentSortState);
    }

    TransactionSortState getCurrentSortState() {
        return currentSortState;
    }

    private void notifySortStateChanged() {
        TransactionSortState nextState = computeSortState();
        if (!nextState.equals(currentSortState)) {
            currentSortState = nextState;
            if (sortStateListener != null) {
                sortStateListener.accept(nextState);
            }
        }
    }

    private TransactionSortState computeSortState() {
        List<TableColumn<Transaction, ?>> order = transactionTable.getSortOrder();
        if (order.isEmpty()) {
            return TransactionSortState.defaultState();
        }

        TableColumn<Transaction, ?> primary = order.get(0);
        TransactionSortKey key = sortKeyForColumn(primary);
        SortDirection direction = primary.getSortType() == SortType.ASCENDING
                ? SortDirection.ASCENDING
                : SortDirection.DESCENDING;
        return new TransactionSortState(key, direction);
    }

    private TransactionSortKey sortKeyForColumn(TableColumn<Transaction, ?> column) {
        if (column == directionColumn) {
            return TransactionSortKey.DIRECTION;
        }
        if (column == otherPartyColumn) {
            return TransactionSortKey.OTHER_PARTY;
        }
        if (column == amountColumn) {
            return TransactionSortKey.AMOUNT;
        }
        if (column == descriptionColumn) {
            return TransactionSortKey.DESCRIPTION;
        }
        if (column == statusColumn) {
            return TransactionSortKey.STATUS;
        }
        if (column == dateColumn) {
            return TransactionSortKey.DATE;
        }
        return TransactionSortKey.AMOUNT;
    }

    /**
     * Returns the title shown when no person is selected.
     */
    static String noSelectionTitle() {
        return NO_SELECTION_TITLE;
    }

    /**
     * Returns the label shown in the status column.
     */
    static String statusText() {
        return STATUS_PENDING;
    }

    /**
     * Returns the status label for the given transaction.
     */
    static String statusText(Transaction transaction) {
        Objects.requireNonNull(transaction);
        return transaction.isSettled() ? STATUS_SETTLED : STATUS_PENDING;
    }

    /**
     * Formats the amount as an absolute currency string (e.g. "$12.50").
     */
    static String amountText(double amount) {
        return String.format("$%.2f", Math.abs(amount));
    }

    /**
     * Returns the description text shown in the table.
     */
    static String descriptionText(Transaction transaction) {
        Objects.requireNonNull(transaction);
        return transaction.getDescription();
    }

    /**
     * Returns the date text shown in the table.
     */
    static String dateText(Transaction transaction) {
        Objects.requireNonNull(transaction);
        return transaction.getDate().toString();
    }

    /**
     * Sorts transactions by the default transaction ordering.
     */
    static List<Transaction> sortedTransactions(Person person, Iterable<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        Comparator<Transaction> comparator = TransactionComparators.comparatorFor(
                TransactionSortState.defaultState(), person);
        return StreamSupport.stream(transactions.spliterator(), false)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Builds the display model for the given person.
     * If {@code person} is null, returns the no-selection state.
     */
    static DisplayModel displayModelFor(Person person) {
        if (person == null) {
            return new DisplayModel(noSelectionTitle(), List.of());
        }

        String displayTitle = UiMessages.transactionsTitle(person.getTransactions().size(), person.getName().fullName);
        List<Transaction> transactions = sortedTransactions(person, person.getTransactions());
        return new DisplayModel(displayTitle, transactions);
    }

    /**
     * Returns the display text for direction based on person's role in the transaction.
     */
    String directionText(Transaction transaction) {
        if (currentPerson == null) {
            return "";
        }
        return TransactionDisplayHelper.directionText(currentPerson, transaction);
    }

    /**
     * Returns the name of the other party in the transaction.
     */
    String otherPartyName(Transaction transaction) {
        if (currentPerson == null) {
            return "";
        }
        return TransactionDisplayHelper.otherPartyName(currentPerson, transaction);
    }

    /**
     * Maps a direction label to the corresponding style class.
     *
     * @return style class name, or null if the label is not recognised.
     */
    static String styleClassForDirection(String directionLabel) {
        if (directionLabel == null) {
            return null;
        }
        if (DIRECTION_OWE.equalsIgnoreCase(directionLabel)) {
            return STYLE_TX_OWE;
        }
        if (DIRECTION_LENT.equalsIgnoreCase(directionLabel)) {
            return STYLE_TX_LENT;
        }
        return null;
    }

    static TypeCellModel directionCellModel(String item, boolean empty) {
        if (empty || item == null) {
            return new TypeCellModel(null, null);
        }
        return new TypeCellModel(item, styleClassForDirection(item));
    }

    private class DirectionCell extends TableCell<Transaction, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            getStyleClass().removeAll(STYLE_TX_OWE, STYLE_TX_LENT);

            TypeCellModel model = directionCellModel(item, empty);
            setText(model.getText());
            if (model.getStyleClass() != null) {
                getStyleClass().add(model.getStyleClass());
            }
        }
    }

    private class StatusCell extends TableCell<Transaction, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            getStyleClass().remove(STYLE_STATUS_SETTLED);

            if (empty || item == null) {
                setText(null);
                return;
            }

            setText(item);
            if (STATUS_SETTLED.equals(item)) {
                getStyleClass().add(STYLE_STATUS_SETTLED);
            }
        }
    }

    private class TransactionRow extends javafx.scene.control.TableRow<Transaction> {
        @Override
        protected void updateItem(Transaction item, boolean empty) {
            super.updateItem(item, empty);

            getStyleClass().remove(STYLE_ROW_SETTLED);
            if (!empty && item != null && item.isSettled()) {
                getStyleClass().add(STYLE_ROW_SETTLED);
            }
        }
    }
}
