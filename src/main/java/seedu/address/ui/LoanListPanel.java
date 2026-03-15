package seedu.address.ui;

import java.util.Comparator;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;
import seedu.address.model.loan.Loan;
import seedu.address.model.person.Person;

/**
 * Panel that displays transactions (loans) for the currently selected person.
 */
public class LoanListPanel extends UiPart<Region> {

    private static final String FXML = "LoanListPanel.fxml";

    @FXML
    private Label title;

    @FXML
    private TableView<Loan> loanTable;

    @FXML
    private TableColumn<Loan, Number> indexColumn;

    @FXML
    private TableColumn<Loan, String> typeColumn;

    @FXML
    private TableColumn<Loan, String> amountColumn;

    @FXML
    private TableColumn<Loan, String> descriptionColumn;

    @FXML
    private TableColumn<Loan, String> statusColumn;

    @FXML
    private TableColumn<Loan, String> dateColumn;

    public LoanListPanel() {
        super(FXML);
        showNoSelection();
    }

    @FXML
    private void initialize() {
        indexColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(loanTable.getItems().indexOf(cellData.getValue()) + 1));

        typeColumn.setCellValueFactory(cellData -> {
            double amount = cellData.getValue().getCurrAmount();
            return new ReadOnlyStringWrapper(amount >= 0 ? "Owe" : "Lent");
        });
        typeColumn.setCellFactory(col -> new TransactionTypeCell());

        amountColumn.setCellValueFactory(cellData -> {
            double amount = cellData.getValue().getCurrAmount();
            return new ReadOnlyStringWrapper(String.format("$%.2f", Math.abs(amount)));
        });

        descriptionColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getDescription()));

        statusColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("Pending"));

        dateColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getLastRecalculatedDate().toString()));
    }

    public void displayPerson(Person person) {
        if (person == null) {
            showNoSelection();
            return;
        }

        title.setText("Transactions (" + person.getLoans().size() + ") - " + person.getName().fullName);

        ObservableList<Loan> items = FXCollections.observableArrayList();
        person.getLoans().stream()
                .sorted(Comparator.comparingDouble(Loan::getCurrAmount).reversed())
                .forEach(items::add);
        loanTable.setItems(items);
    }

    private void showNoSelection() {
        title.setText("Transactions - Select a person");
        loanTable.setItems(FXCollections.observableArrayList());
    }

    private static class TransactionTypeCell extends TableCell<Loan, String> {
        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            getStyleClass().removeAll("tx-type-owe", "tx-type-lent");

            if (empty || item == null) {
                setText(null);
                return;
            }

            setText(item);
            if ("Owe".equalsIgnoreCase(item)) {
                getStyleClass().add("tx-type-owe");
            } else if ("Lent".equalsIgnoreCase(item)) {
                getStyleClass().add("tx-type-lent");
            }
        }
    }
}

