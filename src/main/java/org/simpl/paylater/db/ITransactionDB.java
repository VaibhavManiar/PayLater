package org.simpl.paylater.db;

import org.simpl.paylater.pojo.Transaction;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public interface ITransactionDB {
    void addTransaction(String fromUser, Transaction transaction);
    Set<Transaction> getTransactionsForCustomer(String customerId);
    Set<Transaction> getTransactionsForMerchant(String merchantId);
    Map<String, Map<String, Transaction>> getAllTransactions();
}
