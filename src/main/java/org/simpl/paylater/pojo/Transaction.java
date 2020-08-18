package org.simpl.paylater.pojo;

public class Transaction {
    private final String id;
    private final String parentTransactionId;
    private String from;
    private String to;
    private Type type;
    private double amount;
    private Status status;


    public enum Status {
        Dues, Paid
    }

    public enum Type {
        Discount, Buy, Payback
    }

    public Transaction(String id, String parentTransactionId, String from, String to, Type type, double amount, Status status) {
        this.id = id;
        this.parentTransactionId = parentTransactionId;
        this.from = from;
        this.to = to;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getParentTransactionId() {
        return parentTransactionId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
