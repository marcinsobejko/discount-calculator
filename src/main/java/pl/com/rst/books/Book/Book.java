package pl.com.rst.books.Book;

import pl.com.rst.books.Discount.Discount;

import java.util.*;

public class Book {

    // TODO: price should be replaced by Money from Joda-Money
    private float price;

    private Discount largeOrderDiscount;
    private Map<String, Discount> discounToCode;

    private Set<String> usedCodes;

    public Book() {
        this.price = 0.0f;
        this.largeOrderDiscount = null;
        this.discounToCode = new HashMap<>();
        this.usedCodes = new HashSet<>();
    }

    public Book(float price, Discount largeOrderDiscount, Map<String, Discount> discountToCode) {
        this.price = price;
        this.largeOrderDiscount = largeOrderDiscount;
        this.discounToCode = discountToCode;

        this.usedCodes = new HashSet<>();
    }

    public float getPrice() {
        return price;
    }

    public Discount getLargeOrderDiscount() {
        return largeOrderDiscount;
    }

    public Discount getCodeDiscount(String code) {
        return discounToCode.get(code);
    }

    public boolean isCodeNotUsed(String code) {
        return !usedCodes.contains(code);
    }

    public void markDiscountAsUsed(String code) {
        usedCodes.add(code);
    }
}