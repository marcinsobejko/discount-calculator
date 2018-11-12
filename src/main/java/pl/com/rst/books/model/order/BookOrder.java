package pl.com.rst.books.model.order;

import lombok.Data;
import lombok.Getter;
import pl.com.rst.books.model.book.Book;

@Getter
@Data
public class BookOrder {

    private Book            book;
    private Integer         numberOfItems;
    private String[]        discountCodes;
    private OrderSummary    summary;

    public BookOrder(Book book, Integer numberOfItems, String[] discountCodes) {
        this.book = book;
        this.numberOfItems = numberOfItems;
        this.discountCodes = discountCodes;
    }

    public BookOrder(Book book, Integer numberOfItems, String[] discountCodes, OrderSummary summary) {
        this(book, numberOfItems, discountCodes);
    }
}
