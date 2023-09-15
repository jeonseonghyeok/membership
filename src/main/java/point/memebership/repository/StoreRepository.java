package point.memebership.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import point.memebership.domain.Customer;
import point.memebership.domain.Store;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class StoreRepository {

    @PersistenceContext
    private EntityManager em;

    public Store findOne(Long skey){
        return em.find(Store.class, skey);
    }

    public Optional<Store> findbyStoreId(String store_id){
        return Optional.ofNullable(em.createQuery("select s from Store s where s.store_id = :store_id",Store.class).
                setParameter("store_id",store_id).getResultStream().findAny().orElseThrow(() -> new IllegalArgumentException("user doesn't exist")));
    }

    public long save(Store store) {
        em.persist(store);
        return store.getSkey();
    }
}
