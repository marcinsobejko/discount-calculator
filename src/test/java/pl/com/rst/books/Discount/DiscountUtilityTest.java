package pl.com.rst.books.Discount;

import org.junit.Test;
import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookNotFoundException;
import pl.com.rst.books.Book.BookRepository;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class DiscountUtilityTest {

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsEmpty() throws BookNotFoundException {
        // --- GIVEN ---
        final Book book = new Book(100.0f, null, null);

        final BookRepository bookRepository = mock(BookRepository.class);
        final DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        long result = discountUtility.getDiscount("5", 10, "Abc", new HashSet<>());

        // --- THEN ---
        assertEquals(0, result);
    }

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsCodeAndLargeOrder() throws BookNotFoundException {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);
        avaibleDiscounts.add(DiscountType.CODE);

        long result = discountUtility.getDiscount("5", 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(10, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeCode() throws BookNotFoundException {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.CODE);

        long result = discountUtility.getDiscount("5", 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(10, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeCode() throws BookNotFoundException {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);
        book.markDiscountAsUsed("Abc");

        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.CODE);

        long result = discountUtility.getDiscount("5", 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(0, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeLargeOrder() throws BookNotFoundException {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);

        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);

        long result = discountUtility.getDiscount("5", 3000, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(20, result);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeLargeOrder() throws BookNotFoundException {
        // --- GIVEN ---
        Discount largeOrderDiscount = new Discount(20, Discount.DiscountParamType.MONEY);
        Discount discount1002 = new Discount(10, Discount.DiscountParamType.MONEY);

        Map<String, Discount> discountToCode = new HashMap<>();
        discountToCode.put("Abc", discount1002);

        Book book = new Book(100.0f, largeOrderDiscount, discountToCode);
        book.markDiscountAsUsed("Abc");

        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);

        long result = discountUtility.getDiscount("5", 20, "Abc", avaibleDiscounts);

        // --- THEN ---
        assertEquals(0, result);
    }

    @Test(expected = BookNotFoundException.class)
    public void testGetDiscountWhenBookNotExist() throws BookNotFoundException {
        // --- GIVEN ---
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenThrow(BookNotFoundException.class);

        // --- WHEN ---
        Set<DiscountType> avaibleDiscounts = new HashSet<>();
        avaibleDiscounts.add(DiscountType.LARGE_ORDER);

        discountUtility.getDiscount("5", 100, "Abc", avaibleDiscounts);
    }
}