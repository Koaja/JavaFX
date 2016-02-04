package lib_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Model {

    private ObservableList<Book> booksCollection = FXCollections.observableArrayList();
    private BufferedReader readBooksFromFile;
    private BufferedReader input;
    private PrintStream writeBooksToFile;
    private File booksFile;
    private Book book;

    public void addBook(Book b, TextField txtBookAuthor, TextField txtBookTitle, TextField txtBookGenre, Label lblAddMessage, String emptyFields, TextArea txtBooksDisplay) {

        String bookName = txtBookAuthor.getText();
        String bookTitle = txtBookTitle.getText();
        String bookGenre = txtBookGenre.getText();

        if (isTextFieldEmpty(txtBookAuthor) || isTextFieldEmpty(txtBookTitle) || isTextFieldEmpty(txtBookGenre)) {
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
    }

    public String getLabelText(Label label) {
        return label.getText();
    }

    public int NumberOfBooks() {
        return booksCollection.size();
    }

    public boolean isTextFieldEmpty(TextField txtField) {
        return txtField.getText().isEmpty();
    }

    public void listLibrary(TextArea txtBooksDisplay) {

        txtBooksDisplay.clear(); // clears the text area to display the updated list
        if (booksCollection.size() == 0) {
            txtBooksDisplay.setText("No books");
        } else {
            txtBooksDisplay.clear();
            for (Book b : booksCollection) {
                txtBooksDisplay.appendText(b.toString() + "\n");
            }
        }
    }

    public void searchLibrary(TextArea txtSearchDisplay, TextField txtSearch, Label lblAddMessage) {
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
    }
}
