package org.simpl.paylater.service;

import org.simpl.paylater.IdGeneratorUtil;
import org.simpl.paylater.db.CustomerDB;
import org.simpl.paylater.db.MerchantDB;
import org.simpl.paylater.db.TransactionDB;
import org.simpl.paylater.pojo.Customer;
import org.simpl.paylater.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {

    private final AtomicInteger index;

    @Autowired
    private TransactionDB transactionDB;

    @Autowired
    private MerchantDB merchantDB;

    @Autowired
    private CustomerDB customerDB;

    @Value("${simpl.service.id}")
    private String simplId;

    @Value("${simpl.merchant.min.discount.percentage}")
    private String minDiscountPercentage;

    public TransactionService() {
        this.index = new AtomicInteger(1);
    }

    @Override
    public void buy(String product, String userId, String merchantId, double amount) {
        Customer customer = customerDB.get(userId);
        if (customer != null) {
            try {
                customer.getLock().lock();
                if (isCreditLimitBreached(customer, amount)) {
                    throw new RuntimeException("Credit Limit is breached");
                }

                Transaction buyTransaction = createBuyTransaction(this.index.getAndIncrement(), simplId, merchantId, "", amount);
                Transaction duesTransaction = createDuesTransaction(this.index.getAndIncrement(), buyTransaction.getId(), merchantId, simplId, "", (amount * merchantDB.get(merchantId).getDiscountPercentage()) / 100);
                Transaction paybackTransaction = createPayBackTransaction(this.index.getAndIncrement(), buyTransaction.getId(), userId, simplId, "", amount);

                transactionDB.addTransaction(simplId, buyTransaction);
                transactionDB.addTransaction(merchantId, duesTransaction);
                transactionDB.addTransaction(userId, paybackTransaction);
            } finally {
                customer.getLock().unlock();
            }
        } else {
            throw new RuntimeException("Customer not found with customer id : [" + userId + "]");
        }
    }

    @Override
    public void payback(String customerId, double amount) {
        Set<Transaction> transactions = this.transactionDB.getTransactionsForCustomer(customerId).stream().
                filter(trnx -> Transaction.Status.Dues.equals(trnx.getStatus())).collect(Collectors.toSet());
        for (Transaction trnx : transactions) {
            double amt = trnx.getAmount();
            if (amt <= amount) {
                amount -= amt;
                trnx.setAmount(0.0D);
                trnx.setStatus(Transaction.Status.Paid);
            } else {
                amt -= amount;
                amount = 0.0D;
                trnx.setAmount(amt);
            }
            transactionDB.addTransaction(customerId, trnx);
            if (amount == 0.0D) {
                break;
            }
        }
    }

    private static Transaction createBuyTransaction(int index, String from, String to, String product, double amount) {
        return new Transaction(IdGeneratorUtil.generateTransactionId(index), null, from, to, Transaction.Type.Buy, amount, Transaction.Status.Paid);
    }

    private static Transaction createDuesTransaction(int index, String parentTrnxId, String from, String to, String product, double amount) {
        return new Transaction(IdGeneratorUtil.generateTransactionId(index), parentTrnxId, from, to, Transaction.Type.Buy, amount, Transaction.Status.Paid);
    }

    private static Transaction createPayBackTransaction(int index, String parentTrnxId, String from, String to, String product, double amount) {
        return new Transaction(IdGeneratorUtil.generateTransactionId(index), parentTrnxId, from, to, Transaction.Type.Discount, amount, Transaction.Status.Dues);
    }

    private boolean isCreditLimitBreached(Customer customer, double amount) {
        Set<Transaction> transactions = this.transactionDB.getTransactionsForCustomer(customer.getId());
        double totalDues = transactions.stream().filter(trnx -> Transaction.Status.Dues.equals(trnx.getStatus())).
                map(Transaction::getAmount).reduce(0.0D, Double::sum);
        double creditLimit = customer.getCreditLimit();
        creditLimit -= totalDues;
        return amount > creditLimit;
    }
}
