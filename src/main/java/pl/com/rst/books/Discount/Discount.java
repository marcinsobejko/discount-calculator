package pl.com.rst.books.Discount;

public class Discount {

    private int discount;

    private String type;

    public Discount() {}

    public Discount(int discount, String type) {
        this.discount = discount;
        this.type = type;
    }

    public void setDiscount(int discount) { this.discount = discount; }

    public int getDiscount() {
        return discount;
    }

    public void setType(String type) { this.type = type; }

    public String getType() {
        return type;
    }
}
