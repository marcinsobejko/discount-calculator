package pl.com.rst.books.model.discount;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.com.rst.books.model.order.BookOrder;

@EqualsAndHashCode(callSuper = true)
@Data
public class LargeOrderDiscount extends Discount {

    private static final String NAME = "LARGE_ORDER_DISCOUNT";

    private final Integer minTreshold;

    public LargeOrderDiscount(Double value, DiscountType type, Boolean only, Integer minTreshold) {
        super(NAME, value, type, only);
        this.minTreshold = minTreshold;
    }

    @Override
    public boolean checkIfApply(BookOrder order) {
        return order.getNumberOfItems() >= minTreshold;
    }

    @Override
    public Double calculate(BookOrder order) { return getType().calculate(order.getBook().getPrice(), getValue()); }
}
