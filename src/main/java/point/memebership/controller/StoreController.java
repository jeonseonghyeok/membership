package point.memebership.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import point.memebership.domain.PointAccumulate;
import point.memebership.domain.Store;
import point.memebership.service.PointService;
import point.memebership.service.StoreService;

import java.util.List;

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
 * POST 방식으로 JSON 형식을 받을 경우
 * @param requestBody
*/
    @RequestMapping("/pointAcm/{skey}")
    @ResponseBody
    public Long pointAcm(@PathVariable(name = "skey") Integer skey,
                         @RequestBody PointAcmForm pointAcmForm) {

        log.info("pointAcm : " + pointAcmForm.getPoint());
        return pointService.pointACM(skey,pointAcmForm);
    }

    @RequestMapping("/pointSearch/{skey}")
    @ResponseBody
    public List<PointAccumulate> pointSearch(@PathVariable(name = "skey") Integer skey,
                                             @RequestParam(required = false) String phone) {

        log.info("pointSearch : " + phone);
        if(phone == null){
            return pointService.pointAcmSearch(skey);
        }
        else{

            return null;
        }
       // return pointService.pointACM(skey,pointAcmForm);
    }
}