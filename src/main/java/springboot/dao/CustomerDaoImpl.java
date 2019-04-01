package springboot.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import springboot.model.Customer;

import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    private Session session;

    private SessionFactory sessionFactory;

    private static final Logger LOG = LogManager.getLogger(CustomerDaoImpl.class);

    @Autowired
    public CustomerDaoImpl(EntityManagerFactory factory) {
        if (factory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }
        sessionFactory = factory.unwrap(SessionFactory.class);
    }


    @Override
    public Set<Customer> getAllCustomers() {
        Set<Customer> customers = new HashSet<>();
        try {
            session = sessionFactory.openSession();
            customers = new HashSet<>(session.createQuery("FROM Customer", Customer.class).list());
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customers;
    }

    @Override
    public Customer findCustomerById(BigDecimal id) {
        Customer customer = null;
        try {
            session = sessionFactory.openSession();
            customer = session.get(Customer.class, id);
        } catch (Exception sqlException) {
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return customer;
    }

    @Override
    public void insertCustomer(Customer customer) {
        try {
            session = sessionFactory.openSession();

            session.beginTransaction();
            session.persist(customer);
            session.getTransaction().commit();
            LOG.info("Successfully Insert Records In The Database!");
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                LOG.info(".......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try {
            session = sessionFactory.openSession();

            session.beginTransaction();
            session.merge(customer);
            session.getTransaction().commit();
            LOG.info("Customer With Id={} Is Successfully Updated In The Database!", customer.getCustNum());
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                LOG.info(".......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteCustomer(BigDecimal id) {
        try {
            session = sessionFactory.openSession();

            session.beginTransaction();
            session.remove(session.getReference(Customer.class, id));
            session.getTransaction().commit();
            LOG.info("Customer With Id={} Is Successfully deleted from The Database!", id);
        } catch (Exception sqlException) {
            if (null != session.getTransaction()) {
                LOG.info(".......Transaction Is Being Rolled Back.......");
                session.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
