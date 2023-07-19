package point.memebership.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import point.memebership.domain.Customer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Slf4j
@Repository
public class CustomerRepository {

    @PersistenceContext
    private EntityManager em;

    public Customer findOne(Long ckey){
        return em.find(Customer.class, ckey);
    }


    public Customer save(Customer customer) {
        em.persist(customer);
        return customer;
    }

    public Optional<Customer> findOneByphoneNumber(String customerPhone) {
        return em.createQuery("select c from Customer c where c.phone = :phone",Customer.class).
                        setParameter("phone",customerPhone).getResultStream().findAny();
    }
}
