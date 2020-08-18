package org.simpl.paylater.db;

import org.simpl.paylater.pojo.Merchant;
import org.simpl.paylater.pojo.Transaction;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TransactionDB implements ITransactionDB {
    private final Map<String, Map<String, Transaction>> transactionStore;
    private AtomicInteger index;

    public TransactionDB() {
        this.transactionStore = new ConcurrentHashMap<>();
        this.index = new AtomicInteger(1);
    }

    public void addTransaction(String fromUser, Transaction transaction) {
        Map<String, Transaction> transactionMap = transactionStore.getOrDefault(fromUser, new ConcurrentHashMap<>());
        transactionMap.put(transaction.getId(), transaction);
        transactionStore.put(fromUser, transactionMap);
    }

    private Set<Transaction> getTransactions(String customerOrMerchantId) {
        return new HashSet<>(this.transactionStore.getOrDefault(customerOrMerchantId, new HashMap<>()).values());
    }

    @Override
    public Set<Transaction> getTransactionsForCustomer(String customerId) {
        return this.getTransactions(customerId);
    }

    @Override
    public Set<Transaction> getTransactionsForMerchant(String merchantId) {
        return this.getTransactions(merchantId);
    }

    @Override
    public Map<String, Map<String, Transaction>> getAllTransactions() {
        return this.transactionStore;
    }
}
