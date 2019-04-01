package springboot.dao;

import springboot.model.Customer;

import java.math.BigDecimal;
import java.util.Set;

public interface CustomerDao {
    Set<Customer> getAllCustomers();

    Customer findCustomerById(BigDecimal id);

    void insertCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomer(BigDecimal id);
}
