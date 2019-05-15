package pl.com.rst.books.model.discount;

import pl.com.rst.books.model.order.BookOrder;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CodesDiscount extends Discount {

    private static final String NAME = "CODE_DISCOUNT";

    private final Set<String> codes;
    private final Set<String> usedCodes;

    public CodesDiscount(Double value, DiscountType type, Boolean only, Set<String> codes) {
        super(NAME, value, type, only);

        this.codes = codes;
        this.usedCodes = new HashSet<>();
    }

    @Override
    public boolean checkIfApply(BookOrder order) {
        return Arrays.stream(order.getDiscountCodes()).filter(codes::contains).anyMatch(code -> true);
    }

    @Override
    public Double calculate(BookOrder order) {
        double discount = Arrays.stream(order.getDiscountCodes())
                .filter(codes::contains)
                .mapToDouble(code -> {
                    usedCodes.add(code);
                    return getType().calculate(order.getBook().getPrice(), getValue());
                }).sum();

        return discount > order.getBook().getPrice() ? order.getBook().getPrice() : discount;
    }
}
