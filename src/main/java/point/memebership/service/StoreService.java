package point.memebership.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.domain.Customer;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.repository.CustomerRepository;
import point.memebership.repository.PointAccumulateRepository;
import point.memebership.repository.StoreRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    private final CustomerRepository customerRepository;
    private final PointAccumulateRepository acmRepository;

    @Transactional
    public Long join(Store store){
        return storeRepository.save(store);
    }

    public Map SearchCustomerInfo(long skey,String phone){
        Map resultMap = new HashMap<>();
        Store store = storeRepository.findOne(skey);
        Customer customer = customerRepository.findOneByphoneNumber(phone).get();
        resultMap.put("phone",phone);
        resultMap.put("nickname",customer.getNickname());
        resultMap.put("totalpoint",acmRepository.findTotalAcmToCustomer(store,customer));
        return resultMap;
    }

}
