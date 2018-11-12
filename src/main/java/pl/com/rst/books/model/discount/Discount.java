package pl.com.rst.books.model.discount;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.com.rst.books.model.order.BookOrder;

@AllArgsConstructor
@Data
public abstract class Discount {

    private String          name;
    private Double          value;
    private DiscountType    type;
    private Boolean         only;

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
