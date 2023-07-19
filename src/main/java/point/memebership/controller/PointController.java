package point.memebership.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import point.memebership.domain.PointAccumulate;
import point.memebership.service.PointService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("point")
    public String pointTest(Model model) {
//        PointAccumulate pointAccumulate = PointAccumulate.createPoint(300,5);
//        pointService.pointACM(pointAccumulate);
        return "hello";
    }
}