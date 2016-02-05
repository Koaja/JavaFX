package lib_v2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Popup;

public class FXMLDocumentController implements Initializable {

    // buttons
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnDeleteBook;
    @FXML
    private Button btnSearchBook;
    @FXML
    private Button btnClearSearch;
    @FXML
    private Button btnShowBooks;

    // labels
    @FXML
    private Label lblInfo;

    // tooltips
    @FXML
    private Tooltip tooltipInfo;

    // menu items
    @FXML
    private MenuItem menuItemExit;
    @FXML
    private MenuItem menuItemExport;
    @FXML
    private MenuItem menuItemImport;
    @FXML
    private MenuItem menuItemAbout;

    // text areas
    @FXML
    private TextArea txtBooksDisplay;
    @FXML
    private TextArea txtSearchDisplay;

    // text fields
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
    private final String emptyFields = "Fields cannot be empty";
    private final String homeDir = System.getProperty("user.home");

    @FXML
    private void handleButtonAction(ActionEvent e) {

        // adds book to library
        if ((e.getSource() == btnAddBook)) {
            model.addBook(book, txtBookAuthor, txtBookTitle, txtBookGenre, lblInfo, emptyFields, txtBooksDisplay);

            // delete book
        } else if (e.getSource() == btnDeleteBook) {
            lblInfo.setText("Feature not yet implemented");

            // search book
        } else if (e.getSource() == btnSearchBook) {
            model.searchLibrary(txtSearchDisplay, txtSearch, lblInfo);

            // show available books 
        } else if (e.getSource() == btnShowBooks) {
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

            // imports an existing library
        } else if (e.getSource() == menuItemImport) {
            model.importLibrary(homeDir + "//Desktop//books.txt", lblInfo);
        } else if (e.getSource() == menuItemExport) {
            model.exportLibrary();
        } else if (e.getSource() == menuItemAbout) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.setContentText(model.getStageTitle() + "\nVersion: 1.0\nMade by: Koaja");

            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // checks for autor title and genre fields if they are have text
        BooleanBinding bb = new BooleanBinding() {
            {
                super.bind(txtBookAuthor.textProperty(), txtBookTitle.textProperty(), txtBookGenre.textProperty());
            }

            @Override
            protected boolean computeValue() {
                return (txtBookAuthor.getText().isEmpty() || txtBookTitle.getText().isEmpty() || txtBookGenre.getText().isEmpty());
            }
        };
        btnAddBook.disableProperty().bind(bb); // add button is disabled while either one of the text fields are empty
        btnAddBook.setDefaultButton(true); // add button is default button and user can submit a book with Enter keyboard button

        tooltipInfo.textProperty().bind(lblInfo.textProperty()); // adds tooltip to info label 

    }

}
