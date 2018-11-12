package pl.com.rst.books.model.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
@Data
public class DiscountSummary {

    private double  discount;
    Set<Discount>   discounts;

    public boolean hasDiscount() {
        return !discounts.isEmpty();
    }
}
