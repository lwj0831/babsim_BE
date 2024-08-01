package likelion.babsim.domain.point.controller;

import likelion.babsim.domain.point.service.PointService;
import likelion.babsim.web.point.PointLogResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/point")
@CrossOrigin(origins = {"http://localhost:5173"})
public class PointController {

    private final PointService pointService;

    @GetMapping("/log")
    public List<PointLogResDTO> getPointLogByMemberId(@RequestParam("memberId") String memberId) {
        return pointService.getPointLogByMemberId(memberId);
    }

    @GetMapping
    public Integer getPointByMemberId(@RequestParam("memberId") String memberId) {
        return pointService.getPointByMemberId(memberId);
    }
}
