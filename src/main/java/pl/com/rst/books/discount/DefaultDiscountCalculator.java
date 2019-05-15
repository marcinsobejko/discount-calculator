package pl.com.rst.books.discount;

import com.google.common.collect.Sets;
import pl.com.rst.books.model.discount.Discount;
import pl.com.rst.books.model.discount.DiscountSummary;
import pl.com.rst.books.model.order.BookOrder;

import java.util.List;
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
        return findOnlyDiscount(order)
                .map(disc -> (Set<Discount>) Sets.newHashSet(disc))
                .orElseGet(() -> findOtherDiscounts(order));
    }

    private Optional<Discount> findOnlyDiscount(BookOrder order) {
        List<Discount> discounts = order.getBook().getDiscounts().stream().
                filter(d -> d.getOnly() && d.checkIfApply(order))
                .collect(Collectors.toList());

        if (discounts.size() > 1) {
            throw new IllegalStateException("More than one ONLY discount");
        } else {
            return discounts.isEmpty() ? Optional.empty() : Optional.of(discounts.get(0));
        }
    }

    private Set<Discount> findOtherDiscounts(BookOrder order) {
        return order.getBook().getDiscounts().stream().filter(d -> d.checkIfApply(order)).collect(Collectors.toSet());
    }

    private double calculateDiscount(BookOrder order, Set<Discount> discounts) {
        double discountSum = discounts.stream().mapToDouble(d -> d.calculate(order)).sum();
        return discountSum > order.getBook().getPrice() ? order.getBook().getPrice() : discountSum;
    }
}
