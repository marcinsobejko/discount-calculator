package pl.com.rst.books.Discount;

import pl.com.rst.books.Book.Book;
import pl.com.rst.books.Book.BookNotFoundException;
import pl.com.rst.books.Book.BookRepository;

public class DiscountUtility {

    public long getDiscount(String bookId, float orderPrice, String discountCode, String[] availableDiscounts) throws BookNotFoundException {
        if (availableDiscounts.length == 0 || availableDiscounts == null) {
            return 0;
        }

        long id;
        Book book;
        boolean discountAlreadyCalculated = false;
        float totalDiscount = 0;

        for (String discount : availableDiscounts) {
            switch (discount) {
                case "large-order":
                    if (!discountAlreadyCalculated) {
                        id = Long.valueOf(bookId);
                        book = getBookRepository().getBook(id);

                        Discount largeOrderDiscount = book.getLargeOrderDiscount();
                        if (orderPrice > 300) {
                            totalDiscount += book.getPrice() * Utils.percentToFloat(largeOrderDiscount.getDiscount());
                        }
                    }

                    break;
                case "code":
                    id = Long.valueOf(bookId);
                    book = getBookRepository().getBook(id);

                    if (book.isCodeNotUsed(discountCode)) {
                        Discount codeDiscount = book.getCodeDiscount(discountCode);
                        if (codeDiscount.getType() == "percent") {
                            totalDiscount += book.getPrice() * Utils.percentToFloat(codeDiscount.getDiscount());
                        } else if (codeDiscount.getType() == "money"){
                            totalDiscount += codeDiscount.getDiscount();
                        } else {
                            throw new IllegalArgumentException("Not recognized discount type");
                        }

                        book.markDiscountAsUsed(discountCode);
                    }

                    discountAlreadyCalculated = true;
                    break;
            }
        }

        return (long) totalDiscount;
    }


    public BookRepository getBookRepository() {
        return new BookRepository();
    }
}
