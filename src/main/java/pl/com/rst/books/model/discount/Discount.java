package pl.com.rst.books.model.discount;

import lombok.Data;
import pl.com.rst.books.model.order.BookOrder;

@Data
public abstract class Discount {

    private final String          name;
    private final Double          value;
    private final DiscountType    type;
    private final Boolean         only;

    public enum DiscountType {

        MONEY {

            @Override
            public Double calculate(Double price, Double discount) {
                return discount;
            }
        },

        PERCENT {

            @Override
            public Double calculate(Double price, Double discount) {
                return discount > 0 ? price / (100.0d / discount) : 0;
            }
        };

        abstract public Double calculate(Double price, Double discount);
    }

    abstract public boolean checkIfApply(BookOrder order);

    abstract public Double calculate(BookOrder order);
}
