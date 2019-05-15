package pl.com.rst.books.discount;

import org.junit.Test;
import com.google.common.collect.Sets;
import pl.com.rst.books.model.book.Book;
import pl.com.rst.books.model.discount.Discount;
import pl.com.rst.books.model.discount.DiscountSummary;
import pl.com.rst.books.model.discount.LargeOrderDiscount;
import pl.com.rst.books.model.order.BookOrder;
import pl.com.rst.books.model.discount.CodesDiscount;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class DiscountCalculatorTest {

    private static final String DISCOUNT_CODE_AF = "AF";
    private static final String DISCOUNT_CODE_GC = "GC";

    private DiscountCalculator discountCalculator = new DefaultDiscountCalculator();

    private Discount largeOrderDiscountPercentType = new LargeOrderDiscount(25.0d, Discount.DiscountType.PERCENT, false, 20);
    private Discount largeOrderDiscountMoneyType = new LargeOrderDiscount(50.0d, Discount.DiscountType.MONEY, false, 20);

    private Discount codeDiscountPercentType = new CodesDiscount(10.0d, Discount.DiscountType.PERCENT, false, Sets.newHashSet(DISCOUNT_CODE_AF, DISCOUNT_CODE_GC));
    private Discount codeDiscountMoneyType = new CodesDiscount(45.0d, Discount.DiscountType.MONEY, false, Sets.newHashSet(DISCOUNT_CODE_AF, DISCOUNT_CODE_GC));

    private Discount codeDiscountPercentTypeOnly = new CodesDiscount(45.0d, Discount.DiscountType.PERCENT, true, Sets.newHashSet(DISCOUNT_CODE_AF, DISCOUNT_CODE_GC));
    private Discount codeDiscountMoneyTypeOnly = new CodesDiscount(45.0d, Discount.DiscountType.MONEY, true, Sets.newHashSet(DISCOUNT_CODE_AF, DISCOUNT_CODE_GC));

    @Test
    public void testCalculateLargeOrderDiscountPercentType() {
        // --- GIVEN ---
        Set<Discount> discounts = Sets.newHashSet(largeOrderDiscountPercentType);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, null);

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(25.0d, discounts);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateLargeOrderDiscountMoneyType() {
        // --- GIVEN ---
        Set<Discount> discounts = Sets.newHashSet(largeOrderDiscountMoneyType);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, null);

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(50.0d, discounts);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateLargeOrderDiscountWhenDiscountDontApply() {
        // --- GIVEN ---
        Set<Discount> discounts = Sets.newHashSet(largeOrderDiscountPercentType);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 10, null);

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(0, Sets.newHashSet());

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateCodesDiscountPercentType() {
        Set<Discount> discounts = Sets.newHashSet(codeDiscountPercentType);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, new String[] {DISCOUNT_CODE_AF, DISCOUNT_CODE_GC});

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(20.0d, discounts);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateCodesDiscountMoneyType() {
        Set<Discount> discounts = Sets.newHashSet(codeDiscountMoneyType);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, new String[] {DISCOUNT_CODE_AF, DISCOUNT_CODE_GC});

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(90.0d, discounts);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateCodesDiscountMoneyTypeOnly() {
        Set<Discount> discounts = Sets.newHashSet(largeOrderDiscountMoneyType, codeDiscountMoneyTypeOnly);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, new String[] {DISCOUNT_CODE_AF, DISCOUNT_CODE_GC});

        // --- WHEN ---
        DiscountSummary actual = discountCalculator.calculate(order);

        // --- THEN ---
        DiscountSummary expected = new DiscountSummary(90.0d, Sets.newHashSet(codeDiscountMoneyTypeOnly));

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testCalculateDiscountWhenMoreThatOneOnlyDiscount() {
        Set<Discount> discounts = Sets.newHashSet(codeDiscountPercentTypeOnly, codeDiscountMoneyTypeOnly);
        Book book = new Book(100.0d, discounts);
        BookOrder order = new BookOrder(book, 20, new String[] {DISCOUNT_CODE_AF, DISCOUNT_CODE_GC});

        // --- WHEN ---
        assertThatThrownBy(() -> discountCalculator.calculate(order)).isInstanceOf(IllegalStateException.class);
    }
}
