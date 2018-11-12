package pl.com.rst.books.discount;

import com.google.common.collect.Sets;
import pl.com.rst.books.model.discount.Discount;
import pl.com.rst.books.model.discount.DiscountSummary;
import pl.com.rst.books.model.order.BookOrder;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultDiscountCalculator implements DiscountCalculator {

    @Override
    public DiscountSummary calculate(BookOrder order) {
        Set<Discount> discounts = findSuitableDiscounts(order);
        double discount = calculateDiscount(order, discounts);

        return new DiscountSummary(discount, discounts);
    }

    private Set<Discount> findSuitableDiscounts(BookOrder order) {
        Optional<Discount> optDiscount = order.getBook().getDiscounts().stream()
                .filter(d -> d.getOnly() && d.checkIfApply(order)).findAny();

        if(optDiscount.isPresent()) {
            return Sets.newHashSet(optDiscount.get());
        }

        return order.getBook().getDiscounts().stream().filter(d -> d.checkIfApply(order)).collect(Collectors.toSet());
    }

    private double calculateDiscount(BookOrder order, Set<Discount> discounts) {
        double discountSum = discounts.stream().mapToDouble(d -> d.calculate(order)).sum();

        return discountSum > order.getBook().getPrice() ? order.getBook().getPrice() : discountSum;
    }
}
