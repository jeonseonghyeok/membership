package point.memebership.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import point.memebership.domain.Store;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@Repository
public class StoreRepository {

    @PersistenceContext
    private EntityManager em;

    public Store findOne(Long skey){
        return em.find(Store.class, skey);
    }


    public long save(Store store) {
        em.persist(store);
        return store.getSkey();
    }
}
