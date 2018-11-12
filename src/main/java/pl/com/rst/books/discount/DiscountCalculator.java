package pl.com.rst.books.discount;

import pl.com.rst.books.model.discount.DiscountSummary;
import pl.com.rst.books.model.order.BookOrder;

public interface DiscountCalculator {

    DiscountSummary calculate(BookOrder order);
}
