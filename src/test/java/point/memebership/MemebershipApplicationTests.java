package point.memebership;

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.domain.Store;
import point.memebership.repository.StoreRepository;
import point.memebership.service.StoreService;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemebershipApplicationTests {

	@Autowired StoreRepository storeRepository;

	@Test
	@Transactional
	@Rollback(false)
	void contextLoads() {
		Store store = Store.createStore("가게","010-3333-4444","경기도 부천시");
		storeRepository.save(store);
	}

}
