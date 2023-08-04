package point.memebership.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.service.PointService;
import point.memebership.service.StoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/store")
public class StoreController {
    private final StoreService storeService;
    private final PointService pointService;

    @PostMapping("/join")
    @ResponseBody
    public Store join(@RequestBody Store store) {
        storeService.join(store);
        return store;
    }
/**
 * 기능 : 포인트 적립
 * 구현 : skey를 path로 사용하며 PointAcmForm 데이터가 전송되면 적립한다.
*/
    @RequestMapping("/pointAcm/{skey}")
    @ResponseBody
    public Long pointAcm(@PathVariable(name = "skey") Integer skey,
                         @RequestBody PointAcmForm pointAcmForm) {

        log.info("pointAcm : " + pointAcmForm.getPoint());
        return pointService.pointACM(skey,pointAcmForm);
    }

    /**
     *
     * @param skey
     * @param phone
     * @return 파라미터가 없으면 전체, 있다면 해당 고객것만 리턴
     */
    @RequestMapping("/pointSearch/{skey}")
    @ResponseBody
    public List<PointAccumulate> pointSearch(@PathVariable(name = "skey") Integer skey,
                                             @RequestParam(required = false) String phone) {

        log.info("pointSearch : " + phone);
        if(phone == null){
            return pointService.SearchAcm(skey);
        }
        else{

            return null;
        }
       // return pointService.pointACM(skey,pointAcmForm);
    }
    @GetMapping("/SearchCustomerInfo/{skey}")
    @ResponseBody
    public Map SearchToCustomer(@PathVariable(name = "skey") Integer skey,
                                @RequestParam String customer_phone) {

        return storeService.SearchCustomerInfo(skey,customer_phone);

    }
}