package point.memebership.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import point.memebership.controller.PointAcmForm;
import point.memebership.domain.Customer;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class PointAccumulateRepository {

    @PersistenceContext
    private EntityManager em;

    public long acm_create(Store store, Customer customer, PointAcmForm pointAcmForm) {
        PointAccumulate pointAcm = PointAccumulate.createPoint(store, customer, pointAcmForm.getPoint(), pointAcmForm.getExpiration());
        em.persist(pointAcm);
        log.info("point id // " + String.valueOf(pointAcm.getAcm_key()));
        return pointAcm.getAcm_key();
    }

    public List<PointAccumulate> findAcmList(Store store, int offset, int limit) {
        List<PointAccumulate> pointAcmList;
        pointAcmList = em.createQuery("select acm from PointAccumulate acm where acm.store = :store", PointAccumulate.class)
                .setParameter("store", store)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return pointAcmList;
    }

    public int findTotalRmnOfCustomer(Store store, Customer customer) {
        int totalAcm = -1;
        try {
            Query query = em.createQuery(
                    "select COALESCE(SUM(acm.rmn_point),0) " +
                            "from PointAccumulate acm " +
                            "where acm.store = :store " +
                            "and acm.customer = : customer " +
                            "and expiration_date > : now " +
                            "group by acm.customer ");
            Object object = query
                    .setParameter("store", store)
                    .setParameter("customer", customer)
                    .setParameter("now", LocalDateTime.now())
                    .getSingleResult();
            log.info("findTotalAcmToCustomer : " + object.toString());
            totalAcm = Integer.parseInt(object.toString());
        } catch (NoResultException nre) {
            totalAcm = 0;
        }
        return totalAcm;
    }

    public Optional<PointAccumulate> findLatestAcm(Store store, Customer customer) {
        Optional<PointAccumulate> optAcm = null;
        optAcm = em.createQuery(
                        "select acm " +
                                "from PointAccumulate acm " +
                                "where acm.store = :store " +
                                "and acm.customer = : customer " +
                                "order by acm.create_date desc ", PointAccumulate.class)
                .setParameter("store", store)
                .setParameter("customer", customer)
                .setMaxResults(1)
                .getResultStream().findAny();
        return optAcm;
    }
}
