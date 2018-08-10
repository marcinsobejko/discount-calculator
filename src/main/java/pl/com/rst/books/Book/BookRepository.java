package pl.com.rst.books.Book;

public interface BookRepository {

    public Book getBook(long id) throws BookNotFoundException;
}
