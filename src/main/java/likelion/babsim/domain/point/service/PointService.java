package likelion.babsim.domain.point.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.point.Point;
import likelion.babsim.domain.point.PointType;
import likelion.babsim.domain.point.repository.PointRepository;
import likelion.babsim.web.point.PointLogResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final MemberRepository memberRepository;

    public List<PointLogResDTO> getPointLogByMemberId(String memberId) {
        List<Point> pointLogs = pointRepository.findAllByMemberId(memberId);
        List<PointLogResDTO> pointLogResDTOs = new ArrayList<>();

        for (Point point : pointLogs) {
            PointLogResDTO pointLogResDTO = PointLogResDTO.builder()
                    .id(point.getId())
                    .pointContent(point.getPointContent())
                    .pointPrice(point.getPointPrice())
                    .pointType(point.getPointType())
                    .build();
            pointLogResDTOs.add(pointLogResDTO);
        }

        return pointLogResDTOs;
    }

    public Integer getPointByMemberId(String memberId) {
        List<Point> pointLogs = pointRepository.findAllByMemberId(memberId);

        Integer total = 0;
        for (Point point : pointLogs) {
            if (point.getPointType().equals(PointType.BUY))
                total -= point.getPointPrice();
            else if (point.getPointType().equals(PointType.SELL))
                total += point.getPointPrice();
            else if (point.getPointType().equals(PointType.REWARD))
                total += point.getPointPrice();
        }

        return total;
    }

    public boolean makePointTransactions(String buyerId, String sellerId, String pointContent, Integer pointPrice) {
        try {
            Point buyerPoint = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.BUY)
                    .member(memberRepository.findById(buyerId).orElseThrow())
                    .build();

            Point sellerPoint = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.SELL)
                    .member(memberRepository.findById(sellerId).orElseThrow())
                    .build();

            pointRepository.save(buyerPoint);
            pointRepository.save(sellerPoint);

            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void givePointReward(String memberId, String pointContent, Integer pointPrice) {
        try {
            Point point = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.REWARD)
                    .member(memberRepository.findById(memberId).orElseThrow())
                    .build();

            pointRepository.save(point);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
