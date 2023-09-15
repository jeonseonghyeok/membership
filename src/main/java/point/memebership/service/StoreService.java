package point.memebership.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import point.memebership.domain.Customer;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.repository.CustomerRepository;
import point.memebership.repository.PointAccumulateRepository;
import point.memebership.repository.StoreRepository;
import point.memebership.security.JwtProvider;

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
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtProvider jwtProvider;

	@Transactional
	public Long signup(Store store){
		store.setStore_pw(new BCryptPasswordEncoder().encode(store.getStore_pw()));
		return storeRepository.save(store);
	}
	//  https://gksdudrb922.tistory.com/217
	@Transactional
	public String login(String store_id, String store_pw){
		// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
		log.info("진입");
		// 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(store_id, store_pw);
		log.info("진행");
		log.info(authenticationToken.toString());
		// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
		// authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		log.info("검증완료");
//		log.info(authentication.getName());
//		3. 인증 정보를 기반으로 JWT 토큰 생성
		String tokenInfo = jwtProvider.generateToken(authentication.getName());
		log.info("생성");
		return tokenInfo;
	}

	public Map<String, String> SearchCustomerInfo(long skey,String phone){
		Map<String, String> resultMap = new HashMap<String,String>();
		resultMap.put("phone", phone);
		try {
			Store store = storeRepository.findOne(skey);
			Customer customer = customerRepository.findOneByphoneNumber(phone).get();
			int totalRmn = acmRepository.findTotalRmnOfCustomer(store, customer);
			Optional<PointAccumulate> latestAcm = acmRepository.findLatestAcm(store, customer);
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
			resultMap.put("nickname", customer.getNickname());
			resultMap.put("totalpoint", String.valueOf(totalRmn));
			resultMap.put("latestAcmDate", latestAcm.get().getCreate_date().format(formatters));
		}catch (NoSuchElementException nse){
			log.debug(nse.toString());
		}
		return resultMap;
	}

}
