package pl.com.rst.books.model.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Data
public class DiscountSummary {

    private double          discount;
    private Set<Discount>   discounts;

    public boolean hasDiscount() {
        return !discounts.isEmpty();
    }
}
