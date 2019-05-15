package pl.com.rst.books.model.discount;

import lombok.Data;

import java.util.Set;

@Data
public class DiscountSummary {

    private final double          discount;
    private final Set<Discount>   discounts;

    public boolean hasDiscount() {
        return !discounts.isEmpty();
    }
}
