package pl.com.rst.books.model.book;

import lombok.AllArgsConstructor;
import lombok.Data;
import pl.com.rst.books.model.discount.Discount;

import java.util.*;

@AllArgsConstructor
@Data
public class Book {

    private double          price;
    private Set<Discount>   discounts;
}