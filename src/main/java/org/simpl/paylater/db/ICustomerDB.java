package org.simpl.paylater.db;

import org.simpl.paylater.pojo.Customer;

import java.util.ArrayList;
import java.util.List;

public interface ICustomerDB {
    void add(Customer customer);
    Customer get(String id);
    List<Customer> getAllCustomers();
}
