package pl.com.rst.books.Discount;

import pl.com.rst.books.Book.Book;

public enum DiscountType {

    LARGE_ORDER {

        private static final double ORDER_PRICE_TRESHOLD_FOR_LARGE_ORDER = 300;

        /**
         * {@inheritDoc}
         *
         * In this case we are not using discountCode
         */
        @Override
        public double calculate(final Book book, final float orderPrice, final String discountCode) {
            if (orderPrice > ORDER_PRICE_TRESHOLD_FOR_LARGE_ORDER) {
                return book.getPrice() * Utils.percentToFloat(book.getLargeOrderDiscount().getDiscount());
            }

            return 0.0f;
        }
    },

    CODE {

        @Override
        public double calculate(final Book book, final float orderPrice, final String discountCode) {
            double discount = 0.0f;

            if (book.isCodeNotUsed(discountCode)) {
                Discount codeDiscount = book.getCodeDiscount(discountCode);

                switch (codeDiscount.getType()) {
                    case MONEY:
                        discount = codeDiscount.getDiscount();
                        break;

                    case PERCENT:
                        discount = book.getPrice() * Utils.percentToFloat(codeDiscount.getDiscount());
                        break;

                    default:
                        throw new IllegalArgumentException("Not recognized discount type");
                }

                book.markDiscountAsUsed(discountCode);
            }

            return discount;
        }
    };

    public abstract double calculate(final Book book, final float orderPrice, final String discountCode);
}
