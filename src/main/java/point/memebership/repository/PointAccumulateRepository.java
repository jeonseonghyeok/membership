package point.memebership.repository;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
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

    public List<PointAccumulate> findList(Store store, int offset, int limit) {
        List<PointAccumulate> pointAcmList;
        pointAcmList = em.createQuery("select acm from PointAccumulate acm where acm.store = :store", PointAccumulate.class)
                .setParameter("store", store)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
        return pointAcmList;
    }

    public int findTotalAcmToCustomer(Store store, Customer customer) {
        int totalAcm = 0;
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
}
