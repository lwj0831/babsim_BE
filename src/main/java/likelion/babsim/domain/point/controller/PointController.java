package likelion.babsim.domain.point.controller;

import likelion.babsim.domain.point.service.PointService;
import likelion.babsim.web.point.PointLogResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class PointController {

    private final PointService pointService;

    @GetMapping("/log")
    public List<PointLogResDTO> getPointLogByMemberId(@RequestParam("memberId") String memberId) {
        return pointService.getPointLogByMemberId(memberId);
    }

    @GetMapping
    public BigDecimal getPointByMemberId(@RequestParam("memberId") String memberId) {
        return pointService.getPointByMemberId(memberId);
    }
}
