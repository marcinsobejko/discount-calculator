package pl.com.rst.books.Discount;

import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookNotFoundException;
import pl.com.rst.books.Book.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class DiscountUtility {

    private static final float ORDER_PRICE_TRESHOLD_FOR_LARGE_ORDER = 300;

    private DiscountUtility() {
        // Not used in utils class
    }

    public static long getDiscount(Book book, float orderPrice, String discountCode, Set<DiscountType> availableDiscounts) {
        return (long) availableDiscounts.stream().mapToDouble(d -> d.calculate(book, orderPrice, discountCode)).sum();
    }
}
