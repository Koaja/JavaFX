package lib_v2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;

public class Model {

    private ArrayList<Book> booksCollections = new ArrayList<>();
    private BufferedReader readBooksFromFile;
    private BufferedReader input;
    private PrintStream writeBooksToFile;
    private File booksFile;

    public void addBook(Book b) {
        booksCollections.add(b);
    }

    public int NumberOfBooks() {
        return booksCollections.size();
    }

    public ArrayList<Book> listLibrary() {

        return booksCollections;
    }

    public void searchLibrary(String searchString) {
        boolean searchSuccessful = false;
        if (searchString.length() >= 3) {
            for (Book book : booksCollections) {
                if (book.toString().toLowerCase().contains(searchString)) {
                    System.out.println(book);
                    searchSuccessful = true;
                }
            }
        }

        if (searchSuccessful == false) {
            System.out.println("No matches were found");
        }
    }

    public void importLibrary(String fileLocation) {
        booksFile = new File(fileLocation);

        if (!booksFile.exists()) {
            try {
                System.out.println("\nNo local library was found, so we created one instead.\n");
                booksFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            readBooksFromFile = new BufferedReader(new InputStreamReader(new FileInputStream(booksFile)));

            String line = "";
            Book b;
            while ((line = readBooksFromFile.readLine()) != null) {
                String[] s = line.split(" - ");
                String bookAuthor = s[0];
                String bookTitle = s[1];
                String bookGenre = s[2];
                b = new Book(bookAuthor, bookTitle, bookGenre);
                addBook(b);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
