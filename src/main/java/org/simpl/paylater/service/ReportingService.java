package org.simpl.paylater.service;

import org.simpl.paylater.db.*;
import org.simpl.paylater.pojo.Customer;
import org.simpl.paylater.pojo.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReportingService implements IReportingService {

    @Autowired
    private ITransactionDB transactionDB;

    @Autowired
    private ICustomerDB customerDB;

    @Autowired
    private IMerchantDB merchantDB;

    @Override
    public double getTotalDiscount(String merchantId) {
        Set<Transaction> transactions = this.transactionDB.getTransactionsForMerchant(merchantId);
        return transactions.stream().map(Transaction::getAmount).reduce(0.0D, Double::sum);
    }

    @Override
    public double getTotalDues(String customerId) {
        Set<Transaction> transactions = this.transactionDB.getTransactionsForCustomer(customerId);
        return transactions.stream().filter(trnx-> Transaction.Status.Dues.equals(trnx.getStatus())).
                map(Transaction::getAmount).reduce(0.0D, Double::sum);
    }

    @Override
    public Map<String, Double> getEachUserCreditLimit() {
        Map<String, Double> eachUserCreditLimit = new HashMap<>();
        for(Customer customer : this.customerDB.getAllCustomers()) {
            eachUserCreditLimit.put(customer.getName(), customer.getCreditLimit());
        }
        return eachUserCreditLimit;
    }

    @Override
    public Map<String, Double> getEachUserDues() {
        Map<String, Double> eachUserDues = new HashMap<>();
        for(Customer customer : this.customerDB.getAllCustomers()) {
            eachUserDues.put(customer.getId(), getTotalDues(customer.getId()));
        }
        return eachUserDues;
    }
}
