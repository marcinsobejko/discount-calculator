package pl.com.rst.books.Discount;

public class Discount {

    public static enum DiscountType {

        MONEY("money"),
        PERCENT("percent");

        private String name;

        DiscountType(String name) {
            this.name = name;
        }
    }

    private int discount;

    private DiscountType type;

    public Discount() {}

    public Discount(int discount, DiscountType type) {
        this.discount = discount;
        this.type = type;
    }

    public void setDiscount(int discount) { this.discount = discount; }

    public int getDiscount() {
        return discount;
    }

    public void setType(DiscountType type) { this.type = type; }

    public DiscountType getType() {
        return type;
    }
}