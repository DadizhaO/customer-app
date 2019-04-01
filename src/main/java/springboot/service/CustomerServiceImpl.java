package springboot.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.dao.CustomerDao;
import springboot.model.Customer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;


@Service
public class CustomerServiceImpl implements CustomerService {
    private static final Logger LOG = LogManager.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;

    @Override
    public Set<Customer> getAllCustomers() {
        LOG.debug("getAllCustomers");
        HashSet<Customer> result = new HashSet<>(customerDao.getAllCustomers());
        LOG.debug("getAllCustomers return {} records", result.size());
        return result;
    }

    @Override
    public Customer findCustomerById(BigDecimal id) {
        LOG.debug("findCustomerById, id={}", id);
        Customer result = customerDao.findCustomerById(id);
        LOG.debug("findCustomerById, result={}", result);
        return result;
    }

    @Override
    public void insertCustomer(Customer customer) {
        LOG.debug("insertCustomer, customer={}", customer);
        customerDao.insertCustomer(customer);
        LOG.debug("insertCustomer completed");
    }

    @Override
    public void updateCustomer(Customer customer) {
        LOG.debug("updateCustomer, customer={}", customer);
        customerDao.updateCustomer(customer);
        LOG.debug("updateCustomer completed");
    }

    @Override
    public void deleteCustomer(BigDecimal id) {
        LOG.debug("deleteCustomer, id={}", id);
        customerDao.deleteCustomer(id);
        LOG.debug("deleteCustomer completed");
    }

}
