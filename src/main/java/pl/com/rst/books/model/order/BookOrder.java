package pl.com.rst.books.model.order;

import lombok.Data;
import pl.com.rst.books.model.book.Book;

@Data
public class BookOrder {

    private final Book      book;
    private final Integer   numberOfItems;
    private final String[]  discountCodes;
}
