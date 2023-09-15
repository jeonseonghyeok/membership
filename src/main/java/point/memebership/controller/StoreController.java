package point.memebership.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.security.SecurityUtil;
import point.memebership.service.PointService;
import point.memebership.service.StoreService;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/store")
public class StoreController {
    private final StoreService storeService;
    private final PointService pointService;

    @PostMapping("/signup")
    @ResponseBody
    public String signup(@RequestBody Store store) {
        storeService.signup(store);
        return "success";
    }
    
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestBody Store store) {
    	String result = storeService.login(store.getStore_id(),store.getStore_pw());
        return result;
    }
/**
 * 기능 : 포인트 적립
 * 구현 : skey를 path로 사용하며 PointAcmForm 데이터가 전송되면 적립한다.
*/
    @PostMapping("/pointAcm")
    @ResponseBody
    public Long pointAcm(@AuthenticationPrincipal Object principal,@RequestBody PointAcmForm pointAcmForm) {
//    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        log.info("pointAcm : " + pointAcmForm.getPoint());
//    	log.info("principal : " +principal.toString());
    	
    	pointAcmForm.setSkey(Long.parseLong(principal.toString()));
//        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
//        log.info(SecurityUtil.getCurrentMemberId());
        return pointService.pointACM(pointAcmForm);
    }

    /**
     *
     * @param skey
     * @param phone
     * @return 파라미터가 없으면 전체, 있다면 해당 고객것만 리턴
     */
    @RequestMapping("/pointSearch")
    @ResponseBody
    public List<PointAccumulate> pointSearch(@AuthenticationPrincipal Object principal,
                                             @RequestParam(required = false) String customer_phone) {

        log.info("pointSearch : " + customer_phone);
        if(customer_phone == null){
            return pointService.SearchAcm(Long.parseLong(principal.toString()));
        }
        else{// 미구현

            return null;
        }
       // return pointService.pointACM(skey,pointAcmForm);
    }
    @GetMapping("/SearchCustomerInfo")
    @ResponseBody
    public Map<String, String> SearchToCustomer(@AuthenticationPrincipal Object principal,
                                @RequestParam String customer_phone) {

        return storeService.SearchCustomerInfo(Long.parseLong(principal.toString()),customer_phone);

    }
    @GetMapping("hello")
    public String hello(Model model) {
        log.info("테스트");
        model.addAttribute("data", "hello!!");
        return "hello";
    }
}