package pl.com.rst.books.model.book;

import lombok.Data;
import pl.com.rst.books.model.discount.Discount;

import java.util.*;

@Data
public class Book {

    private final double          price;
    private final Set<Discount>   discounts;
}