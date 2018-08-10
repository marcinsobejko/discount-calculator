package pl.com.rst.books.Discount;

import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookNotFoundException;
import pl.com.rst.books.Book.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DiscountUtility {

    private static final float ORDER_PRICE_TRESHOLD_FOR_LARGE_ORDER = 300;

    public long getDiscount(String bookId, float orderPrice, String discountCode, Set<DiscountType> availableDiscounts) throws BookNotFoundException {
        final Book book = getBookRepository().getBook(Long.valueOf(bookId));

        return (long) availableDiscounts.stream().mapToDouble(d -> d.calculate(book, orderPrice, discountCode)).sum();
    }

    public boolean isArrayEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public BookRepository getBookRepository() {
        return new BookRepository();
    }
}
