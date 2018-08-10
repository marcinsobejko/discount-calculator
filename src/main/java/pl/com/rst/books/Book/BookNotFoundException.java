package pl.com.rst.books.Book;

public class BookNotFoundException extends Exception {

    private static final String ERROR_MESSAGE = "Not found book with id: ";

    public BookNotFoundException(long id) {
        super(ERROR_MESSAGE + id);
    }
}
