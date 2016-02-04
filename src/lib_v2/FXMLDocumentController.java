package lib_v2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;

public class FXMLDocumentController implements Initializable {

    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnDeleteBook;
    @FXML
    private Button btnSearchBook;
    @FXML
    private Button btnClearSearch;

    @FXML
    private Label lblAddMessage;

    @FXML
    private Tooltip tooltipInfo;

    @FXML
    private MenuItem menuItemList;
    @FXML
    private MenuItem menuItemExit;
    @FXML
    private MenuItem menuItemSort;
    @FXML
    private MenuItem menuItemExport;
    @FXML
    private MenuItem menuItemImport;

    @FXML
    private TextArea txtBooksDisplay;
    @FXML
    private TextArea txtSearchDisplay;

    @FXML
    private TextField txtBookAuthor;
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtBookGenre;
    @FXML
    private TextField txtSearch;

    private final Model model = new Model();
    private Book book;
    private String emptyFields = "Fields cannot be empty";
    String message = null;

    @FXML
    private void mouseAction(MouseEvent e) {

    }

    @FXML
    @SuppressWarnings("Convert2Lambda")
    private void handleButtonAction(ActionEvent e) {

        // adds book to library
        if ((e.getSource() == btnAddBook)) {
            model.addBook(book, txtBookAuthor, txtBookTitle, txtBookGenre, lblAddMessage, emptyFields, txtBooksDisplay);

            // delete book
        } else if (e.getSource() == btnDeleteBook) {
            // TO DO

            // search book
        } else if (e.getSource() == btnSearchBook) {
            model.searchLibrary(txtSearchDisplay, txtSearch, lblAddMessage);

            // show available books 
        } else if (e.getSource() == menuItemList) {
            model.listLibrary(txtBooksDisplay);

            // quits program
        } else if (e.getSource() == menuItemExit) {
            Platform.exit();

            // clears the search text box
        } else if (e.getSource() == btnClearSearch) {
            if (model.isTextFieldEmpty(txtSearch) == false) {
                txtSearch.clear();
            }
            txtSearch.requestFocus(); // sets focus back to search box
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(txtBookAuthor.textProperty(), txtBookTitle.textProperty(), txtBookGenre.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (txtBookAuthor.getText().isEmpty() && txtBookTitle.getText().isEmpty() && txtBookGenre.getText().isEmpty());
            }
        };
        btnAddBook.disableProperty().bind(bb);

        btnAddBook.setDefaultButton(true);

    }

}
