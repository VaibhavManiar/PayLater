package org.simpl.paylater.db;

import org.simpl.paylater.IdGeneratorUtil;
import org.simpl.paylater.pojo.Customer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class CustomerDB implements ICustomerDB {
    private final Map<String, Customer> customerStore;
    private AtomicInteger index;

    @Value("${simpl.customer.max.credit.limit}")
    private double creditLimit;

    public CustomerDB() {
        this.customerStore = new ConcurrentHashMap<>();
        this.index = new AtomicInteger(1);
        this.init();
    }

    private void init() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer(IdGeneratorUtil.generateCustomerId(this.index.getAndIncrement()),
                "Cust1", creditLimit));
        customers.add(new Customer(IdGeneratorUtil.generateCustomerId(this.index.getAndIncrement()),
                "Cust2", creditLimit));
        customers.add(new Customer(IdGeneratorUtil.generateCustomerId(this.index.getAndIncrement()),
                "Cust3", creditLimit));
        customers.add(new Customer(IdGeneratorUtil.generateCustomerId(this.index.getAndIncrement()),
                "Cust4", creditLimit));


        this.customerStore.putAll(customers.stream().collect(Collectors.toMap(Customer::getId, customer -> customer)));
    }

    @Override
    public void add(Customer customer) {
        this.customerStore.put(customer.getId(), customer);
    }

    @Override
    public Customer get(String id) {
        return this.customerStore.get(id);
    }

    @Override
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(this.customerStore.values());
    }

}
