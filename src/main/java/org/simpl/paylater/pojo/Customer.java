package org.simpl.paylater.pojo;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Customer {
    private final String id;
    private String name;
    private double creditLimit;
    private ReentrantReadWriteLock.WriteLock lock;

    public Customer(String id, String name, double creditLimit) {
        this.id = id;
        this.name = name;
        this.creditLimit = creditLimit;
        this.lock = new ReentrantReadWriteLock().writeLock();
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

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public ReentrantReadWriteLock.WriteLock getLock() {
        return lock;
    }
}
