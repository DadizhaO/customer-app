package springboot.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import springboot.model.Customer;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class CustomerDaoImplTest {

    @Autowired
    private CustomerDaoImpl customerDao;
    @Autowired
    private DataSource dataSource;

    private static final Customer INSERT = new Customer(BigDecimal.valueOf(333333));
    private static final Customer EXIST_UPDATE = new Customer(BigDecimal.valueOf(1111));
    private static final Customer EXIST_DELETE = new Customer(BigDecimal.valueOf(2222));
    private static final BigDecimal NOT_EXIST_ID = BigDecimal.valueOf(-1);

    @Before
    public void initDb() {
        Resource initSchema = new ClassPathResource("script\\schema.sql");
        Resource data = new ClassPathResource("script\\data.sql");
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(initSchema, data);
        DatabasePopulatorUtils.execute(databasePopulator, dataSource);
    }

    @Test
    public void testGetAllCustomers() {
        Set<Customer> customers = customerDao.getAllCustomers();
        assertTrue(customers.size() >= 2);
        System.out.println(customers.toString());
    }

    @Test
    public void testInsertCustomer() {
        customerDao.insertCustomer(INSERT);
        Set<Customer> customers = customerDao.getAllCustomers();
        assertEquals(3, customers.size());
    }

    @Test
    public void testDeleteCustomer() {
        customerDao.deleteCustomer(EXIST_DELETE.getCustNum());
        Set<Customer> customers = customerDao.getAllCustomers();
        assertFalse(customers.size() >= 2);
        System.out.println(customers);
    }

    @Test
    public void testFindCustomerByIdNotPresent() {
        Customer customer = customerDao.findCustomerById(NOT_EXIST_ID);
        assertNull(customer);
    }

    @Test
    public void testFindCustomerById() {
        Customer customer = customerDao.findCustomerById(EXIST_UPDATE.getCustNum());
        assertNotNull(customer);
    }
}
