package org.simpl.paylater.service;

public interface ITransactionService {
    void buy(String product, String userId, String merchantId, double amount);
    void payback(String userId, double amount);
}
