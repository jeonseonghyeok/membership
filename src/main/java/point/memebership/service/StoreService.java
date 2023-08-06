package point.memebership.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.domain.Customer;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.repository.CustomerRepository;
import point.memebership.repository.PointAccumulateRepository;
import point.memebership.repository.StoreRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
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
        Map resultMap = new HashMap<String,String>();
        resultMap.put("phone", phone);
        try {
            Store store = storeRepository.findOne(skey);
            Customer customer = customerRepository.findOneByphoneNumber(phone).get();
            int totalRmn = acmRepository.findTotalRmnOfCustomer(store, customer);
            Optional<PointAccumulate> latestAcm = acmRepository.findLatestAcm(store, customer);
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
            resultMap.put("nickname", customer.getNickname());
            resultMap.put("totalpoint", totalRmn);
            resultMap.put("latestAcmDate", latestAcm.get().getCreate_date().format(formatters));
        }catch (NoSuchElementException nse){
            log.debug(nse.toString());
        }
        return resultMap;
    }

}
