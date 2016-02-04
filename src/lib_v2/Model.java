package lib_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Comparator;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class Model {

    private final ObservableList<Book> booksCollection = FXCollections.observableArrayList();
    private BufferedReader readBooksFromFile;
    private BufferedReader input;
    private PrintStream writeBooksToFile;
    private File booksFile;
    private Book book;

    public void addBook(Book b) {
        booksCollection.add(b);

    }

    @SuppressWarnings("Convert2Lambda")
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
                    }
                }
            });

            booksCollection.add(book);

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
        sortLibrary();
        int bookIndex = 1;
        txtBooksDisplay.clear(); // clears the text area to display the updated list
        if (booksCollection.isEmpty()) {
            txtBooksDisplay.setText("No books");
        } else {
            txtBooksDisplay.clear();
            for (Book b : booksCollection) {
                txtBooksDisplay.appendText(bookIndex + ". " + b.toString() + "\n");
                bookIndex++;
            }
        }
    }

    public void searchLibrary(TextArea txtSearchDisplay, TextField txtSearch, Label lblInfo) {
        txtSearchDisplay.clear(); // clears the text area to display new results
        String search = txtSearch.getText();
        if (search.length() >= 3) {
            for (Book b : booksCollection) {
                if (b.toString().toLowerCase().contains(search)) {
                    txtSearchDisplay.appendText(b.toString() + "\n");
                    lblInfo.setText("We found something that you might've searched for ");
                } else {
                    lblInfo.setText("Book wasn't found");
                }
            }
        } else if (search.length() == 0) {
            lblInfo.setText("You will have to type something in the 'Search' field");
        } else if (search.length() >= 0 && search.length() < 3) {
            lblInfo.setText("For a more precise result please enter a word with a length bigger than 3 letters");
        }
        txtSearch.clear(); // clears search  field
        txtSearch.requestFocus(); // sets focus to search field 
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void importLibrary(String fileLocation) {
        booksFile = new File(fileLocation);

        if (!booksFile.exists()) {
            try {
                System.out.println("\nNo local library was found, so we will create one for you instead.\n");
                booksFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            readBooksFromFile = new BufferedReader(new InputStreamReader(new FileInputStream(booksFile)));

            @SuppressWarnings("UnusedAssignment")
            String line = "";

            while ((line = readBooksFromFile.readLine()) != null) {
                String[] s = line.split(" - ");
                String bookAuthor = s[0];
                String bookTitle = s[1];
                String bookGenre = s[2];
                book = new Book(bookAuthor, bookTitle, bookGenre);
                addBook(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("Convert2Lambda")
    public void sortLibrary() {
        Collections.sort(booksCollection, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.toString().compareToIgnoreCase(o2.toString());
            }
        });
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public void exportLibrary() {
        booksFile.delete();
        try {
            booksFile.createNewFile();
            writeBooksToFile = new PrintStream(booksFile);
            for (Book b : booksCollection) {
                writeBooksToFile.println(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
