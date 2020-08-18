package org.simpl.paylater.pojo;

public class Merchant {
    private final String id;
    private String name;
    private double discountPercentage;

    public Merchant(String id, String name, double discountPercentage) {
        this.id = id;
        this.name = name;
        this.discountPercentage = discountPercentage;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
