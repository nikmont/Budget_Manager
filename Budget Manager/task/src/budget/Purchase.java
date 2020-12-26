package budget;

import java.io.Serializable;

public class Purchase implements Serializable, Comparable<Purchase> {
    private PurchaseType type;
    private double price;
    private String name;

    public Purchase(PurchaseType type, String name, double price) {
        this.type = type;
        this.price = price;
        this.name = name;
    }

    public PurchaseType getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s $%.2f", name, price);
    }

    @Override
    public int compareTo(Purchase o) {
        if(this.getPrice() < o.getPrice())
            return 1;
        else if(o.getPrice() < this.getPrice())
            return -1;
        return 0;
    }
}
