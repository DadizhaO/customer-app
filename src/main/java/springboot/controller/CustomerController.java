package springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springboot.model.Customer;
import springboot.service.CustomerService;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private static final Logger LOG = LogManager.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public @ResponseBody
    Set<Customer> getAllCustomers() {
        LOG.debug("use getAllCustomers");
        return customerService.getAllCustomers();
    }

    @PostMapping
    public void addOrder(/*@Valid*/ @RequestBody Customer customerRequest) {
        LOG.info("addCustomer start, customerRequest={}", customerRequest);
        customerService.insertCustomer(customerRequest);
        LOG.info("addCustomer end");
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Customer getCustomerById(@PathVariable("id") int id) {
        LOG.info("getCustomerById start, id={}", id);
        Customer result = customerService.findCustomerById(BigDecimal.valueOf(id));
        LOG.info("getCustomerById end");
        return result;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomerById(@PathVariable("id") int id) {
        LOG.info("deleteCustomerById start, id={}", id);
        customerService.deleteCustomer(BigDecimal.valueOf(id));
        LOG.info("deleteCustomerById end");
    }

    @PutMapping("/{id}")
    public void updateCustomerById(@PathVariable("id") int id, @RequestParam("creditLimit") Integer creditLimit) {
        LOG.info("updateCustomerById start, id={}, creditLimit={}", id, creditLimit);
        Customer customer = customerService.findCustomerById(BigDecimal.valueOf(id));
        if (Objects.isNull(customer)) {
            LOG.warn("updateCustomerById cannot update not existing customer");
        } else {
            customer.setCreditLimit(BigDecimal.valueOf(creditLimit));
            customerService.updateCustomer(customer);
        }
        LOG.info("updateCustomerById end");
    }

}
