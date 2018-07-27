package pl.com.rst.books.Discount;

import org.junit.Test;
import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookRepository;
import org.mockito.Mockito;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class DiscountUtilityTest {

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsEmpty() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 10, "Abc", new String[]{});

        // --- THEN ---
        assertEquals(0, result.discount);
    }

    @Test
    public void testGetDiscountWhenAvaibleDiscountsIsCodeAndLargeOrder() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        when(book.getLargeOrderDiscount()).thenReturn(value);
        when(book.isCodeNotUsed(any())).thenReturn(true);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code", "large-order"});

        // --- THEN ---
        assertEquals(10, result.discount);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeCode() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        Mockito.when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        Mockito.when(book.getLargeOrderDiscount()).thenReturn(value);
        Mockito.when(book.isCodeNotUsed(any())).thenReturn(true);


        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code"});

        // --- THEN ---
        assertEquals(10, result.discount);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeCode() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        Mockito.when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        Mockito.when(book.getLargeOrderDiscount()).thenReturn(value);
        Mockito.when(book.isCodeNotUsed(any())).thenReturn(false);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"code"});

        // --- THEN ---
        assertEquals(0, result.discount);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsNotUsedForDiscountTypeLargeOrder() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        Mockito.when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        Mockito.when(book.getLargeOrderDiscount()).thenReturn(value);
        Mockito.when(book.isCodeNotUsed(any())).thenReturn(true);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 3000, "Abc", new String[]{"large-order"});

        // --- THEN ---
        assertEquals(20, result.discount);
    }

    @Test
    public void testGetDiscountWhenDiscountCodeIsUsedForDiscountTypeLargeOrder() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(book);

        Discount value = new Discount();
        value.discount = 10;
        value.type = "money";
        Mockito.when(book.getCodeDiscount(any())).thenReturn(value);

        value = new Discount();
        value.discount = 20;
        value.type = "money";
        Mockito.when(book.getLargeOrderDiscount()).thenReturn(value);
        Mockito.when(book.isCodeNotUsed(any())).thenReturn(false);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 20, "Abc", new String[]{"large-order"});

        // --- THEN ---
        assertEquals(0, result.discount);
    }

    @Test
    public void testGetDiscountWhenBookNotExist() {
        // --- GIVEN ---
        Book book = mock(Book.class);
        BookRepository bookRepository = mock(BookRepository.class);
        DiscountUtility discountUtility = spy(DiscountUtility.class);

        when(book.getPrice()).thenReturn(100f);
        when(discountUtility.getBookRepository()).thenReturn(bookRepository);
        when(bookRepository.getBook(anyLong())).thenReturn(null);

        // --- WHEN ---
        DiscountUtility.DiscountResult result = discountUtility.getDiscount("5", 100, "Abc", new String[]{"large-order"});

        // --- THEN ---
        assertTrue(result.error);
    }
}