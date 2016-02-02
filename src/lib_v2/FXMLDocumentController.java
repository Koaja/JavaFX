package lib_v2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private TextField txtBookName;
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtBookGenre;
    @FXML
    private TextField txtSearch;

    private Model model = new Model();
    private Book book;
    private ArrayList<Book> booksCollection = new ArrayList<>();

    @FXML
    private void handleButtonAction(ActionEvent e) {
        String bookName = txtBookName.getText();
        String bookTitle = txtBookTitle.getText();
        String bookGenre = txtBookGenre.getText();
        String emptyFields = "Fields cannot be empty";

        // adds book to library
        if (e.getSource() == btnAddBook) {
            // notify user that all fields are required
            if (bookName.isEmpty() || bookTitle.isEmpty() || bookGenre.isEmpty()) {
                lblAddMessage.setText(emptyFields);

            } else {

                //add book to collection
                book = new Book(bookName, bookTitle, bookGenre);
                model.addBook(book);

                // notify user book was added successfully
                lblAddMessage.setText(book.toString() + " added to libray");
                // txtBooksDisplay.appendText(book.toString() + "\n");
                // clears the fields after adding a book
                txtBookName.setText("");
                txtBookTitle.setText("");
                txtBookGenre.setText("");
                txtBookName.requestFocus();
            }

            // delete book
        } else if (e.getSource() == btnDeleteBook) {
            lblAddMessage.setText("Book has been deleted.");
        } else if (e.getSource() == btnSearchBook) {

            // show available books 
        } else if (e.getSource() == menuItemList) {
            booksCollection.addAll(model.listLibrary());
            for (Book b : booksCollection) {
                txtBooksDisplay.appendText(b.toString() + "\n");
            }

            // quits program
        } else if (e.getSource() == menuItemExit) {
            Platform.exit();

            // clears the search text box
        } else if (e.getSource() == btnClearSearch) {
            txtSearch.setText("");
            txtSearch.requestFocus(); // sets focus back to search box

            // imports any existing books
        } else if (e.getSource() == menuItemImport) {
            model.importLibrary(System.getProperty("home.dir") + "//Desktop//books.txt");
        } else if (e.getSource() == btnSearchBook) {
            String search = txtSearch.getText();
            for (Book b : booksCollection) {
                if (b.toString().toLowerCase().contains(search)) {
                    txtSearchDisplay.appendText(b.toString());
                }
            }

        }
        lblAddMessage.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                lblAddMessage.setText("ds");
            }

        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
