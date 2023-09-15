package point.memebership.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.controller.PointAcmForm;
import point.memebership.domain.Customer;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.repository.CustomerRepository;
import point.memebership.repository.PointAccumulateRepository;
import point.memebership.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {
    private final PointAccumulateRepository acmRepository;
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;

    @Transactional
    public Long pointACM(PointAcmForm pointAcmForm){
        Store store = storeRepository.findOne(pointAcmForm.getSkey());
        Optional<Customer> optCustomer = customerRepository.findOneByphoneNumber(pointAcmForm.getCustomer_phone());//Customer.createCustomer(pointAcmForm.getCustomer_phone());

        if(!optCustomer.isEmpty()) {
            return acmRepository.acm_create(store,optCustomer.get(), pointAcmForm);
        }
        else{
            log.info("customer is not find, so new customer's info saved");
            Customer customer = customerRepository.save(Customer.createCustomer(pointAcmForm.getCustomer_phone()));
            return acmRepository.acm_create(store, customer, pointAcmForm);
        }
    }
    public List<PointAccumulate> SearchAcm(long skey){
        Store store = storeRepository.findOne(skey);
        return acmRepository.findAcmList(store,0,10);
    }
//    public int SearchTotalAcm(long skey,String phone){
//        Store store = storeRepository.findOne(skey);
//        Customer customer = customerRepository.findOneByphoneNumber(phone).get();
//        return acmRepository.findTotalAcmToCustomer(store,customer);
//    }
}
