package pl.com.rst.books.Discount;

import org.junit.Test;
import pl.com.rst.books.Book.Book;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class DiscountUtilityTest {

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsEmpty() {
        // --- GIVEN ---
        final Book book = new Book(100.0f, null, null);

        // --- WHEN ---
        long result = DiscountUtility.getDiscount(book, 10, "Abc", new HashSet<>());

        // --- THEN ---
        assertEquals(0, result);
    }

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsCodeAndLargeOrder() {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);
        avaibleDiscounts.add(DiscountType.CODE);

        long result = DiscountUtility.getDiscount(book, 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(10, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeCode() {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.CODE);

        long result = DiscountUtility.getDiscount(book, 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(10, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeCode() {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);
        book.markDiscountAsUsed("Abc");

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.CODE);

        long result = DiscountUtility.getDiscount(book, 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(0, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeLargeOrder() {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);

        long result = DiscountUtility.getDiscount(book, 3000, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(20, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeLargeOrder() {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);
        book.markDiscountAsUsed("Abc");

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);

        long result = DiscountUtility.getDiscount(book, 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(0, result);
    }
}