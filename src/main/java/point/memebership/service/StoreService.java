package point.memebership.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.repository.PointAccumulateRepository;
import point.memebership.repository.StoreRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;

    @Transactional
    public Long join(Store store){
        return storeRepository.save(store);
    }

}
