package springboot.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import springboot.dao.CustomerDao;
import springboot.model.Customer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    @Spy
    @InjectMocks
    private CustomerService customerService = new CustomerServiceImpl();
    @Mock
    private CustomerDao customerDaoImpl;

    private Customer customer1 = new Customer();
    private Customer customer2 = new Customer();

    @Test
    public void testGetAllCustomers() {
        Set<Customer> customers = new HashSet<>(Arrays.asList(customer1, customer2));
        doReturn(customers).when(customerDaoImpl).getAllCustomers();
        Set<Customer> result = customerService.getAllCustomers();
        assertTrue(customers.containsAll(result) && result.containsAll(customers));
    }

    @Test
    public void testFindCustomerById() {
        doReturn(customer1).when(customerDaoImpl).findCustomerById(any());
        Customer result = customerService.findCustomerById(BigDecimal.TEN);
        assertEquals(customer1, result);
    }

    @Test
    public void testInsertCustomer() {
        doNothing().when(customerDaoImpl).insertCustomer(any());
        customerService.insertCustomer(customer1);
        verify(customerDaoImpl, times(1)).insertCustomer(any());
    }

    @Test
    public void testUpdateCustomer() {
        doNothing().when(customerDaoImpl).updateCustomer(any());
        customerService.updateCustomer(customer1);
        verify(customerDaoImpl, times(1)).updateCustomer(any());
    }

    @Test
    public void testDeleteCustomer() {
        doNothing().when(customerDaoImpl).deleteCustomer(any());
        customerService.deleteCustomer(customer1.getCustNum());
        verify(customerDaoImpl, times(1)).deleteCustomer(any());
    }
}
