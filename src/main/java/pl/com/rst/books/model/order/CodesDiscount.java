package pl.com.rst.books.model.order;

import pl.com.rst.books.model.discount.Discount;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CodesDiscount extends Discount {

    private static final String NAME = "CODE_DISCOUNT";

    private Set<String> codes;
    private Set<String> usedCodes;

    public CodesDiscount(Double value, DiscountType type, Boolean only, Set<String> codes) {
        super(NAME, value, type, only);

        this.codes = codes;
        this.usedCodes = new HashSet<>();
    }

    @Override
    public boolean checkIfApply(BookOrder order) {
        return Arrays.stream(order.getDiscountCodes()).filter(code -> codes.contains(code)).count() > 0;
    }

    @Override
    public Double calculate(BookOrder order) {
        double discount = Arrays.stream(order.getDiscountCodes())
                .filter(code -> codes.contains(code))
                .mapToDouble(code -> {
                    usedCodes.add(code);
                    return getType().calculate(order.getBook().getPrice(), getValue());
                }).sum();

        return discount > order.getBook().getPrice() ? order.getBook().getPrice() : discount;
    }
}
