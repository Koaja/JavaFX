package lib_v2;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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

    private Model model = new Model();
    private Book book;
    private ObservableList<Book> booksCollection = FXCollections.observableArrayList();

    @FXML
    private void mouseAction(MouseEvent e) {

    }

    @FXML
    private void handleButtonAction(ActionEvent e) {
        String bookName = txtBookAuthor.getText();
        String bookTitle = txtBookTitle.getText();
        String bookGenre = txtBookGenre.getText();
        String emptyFields = "Fields cannot be empty";
        String messageTooltip = lblAddMessage.getText();

        tooltipInfo.setText(messageTooltip);
        lblAddMessage.setTooltip(tooltipInfo);
        // adds book to library

        if (e.getSource() == btnAddBook) {

            // notify user that all fields are required
            if (bookName.isEmpty() || bookTitle.isEmpty() || bookGenre.isEmpty()) {
                lblAddMessage.setTextFill(Color.RED);
                lblAddMessage.setText(emptyFields);
            } else {

                //add book to collection
                book = new Book(bookName, bookTitle, bookGenre);
                booksCollection.addListener(new ListChangeListener<Book>() {
                    @Override
                    public void onChanged(ListChangeListener.Change<? extends Book> c) {
                        lblAddMessage.setTextFill(Color.BLACK);
                        if (c.next() == c.wasAdded()) {
                            lblAddMessage.setText(book.toString() + " added to libray");
                        } else if (c.next() == c.wasRemoved()) {
                            lblAddMessage.setText("Book has been removed");
                        }
                    }
                });

                booksCollection.add(book);
                // notify user book was added successfully
                txtBooksDisplay.appendText(book.toString() + "\n");

                // clears the fields after adding a book
                txtBookAuthor.setText("");
                txtBookTitle.setText("");
                txtBookGenre.setText("");
                txtBookAuthor.requestFocus();
            }

            // delete book
        } else if (e.getSource() == btnDeleteBook) {
            // TO DO

            // search book
        } else if (e.getSource() == btnSearchBook) {
            txtSearchDisplay.clear(); // clears the text area to display new results
            String search = txtSearch.getText();
            if (search.length() >= 3) {
                for (Book b : booksCollection) {
                    if (b.toString().toLowerCase().contains(search)) {
                        txtSearchDisplay.appendText(b.toString() + "\n");
                        lblAddMessage.setText("We found something that you might've searched for ");
                    } else {
                        lblAddMessage.setText("Book wasn't found");
                    }
                }

            } else if (search.length() == 0) {
                lblAddMessage.setText("You will have to type something in the 'Search' field");
            } else if (search.length() >= 0 && search.length() < 3) {
                lblAddMessage.setText("For a more precise result please enter a word with a length bigger than 3 letters");
            }
            txtSearch.clear(); // clears search  field
            txtSearch.requestFocus(); // sets focus to search field 

            // show available books 
        } else if (e.getSource() == menuItemList) {
            txtBooksDisplay.clear(); // clears the text area to display the updated list
            if (booksCollection.size() == 0) {
                txtBooksDisplay.setText("No books");
            } else {
                txtBooksDisplay.clear();
                for (Book b : booksCollection) {
                    txtBooksDisplay.appendText(b.toString() + "\n");
                }
            }

            // quits program
        } else if (e.getSource() == menuItemExit) {
            Platform.exit();

            // clears the search text box
        } else if (e.getSource() == btnClearSearch) {
            if (txtSearch.getText() != null) {
                txtSearch.clear();
            }
            txtSearch.requestFocus(); // sets focus back to search box

            // imports any existing books
        } else if (e.getSource() == menuItemImport) {
            model.importLibrary(System.getProperty("home.dir") + "//Desktop//books.txt");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }

}
