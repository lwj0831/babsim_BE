package likelion.babsim.domain.point.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.point.Point;
import likelion.babsim.domain.point.PointType;
import likelion.babsim.domain.point.repository.PointRepository;
import likelion.babsim.web.point.PointLogResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
                    .transactionDate(point.getTransactionDate())
                    .build();
            pointLogResDTOs.add(pointLogResDTO);
        }

        return pointLogResDTOs;
    }

    public BigDecimal getPointByMemberId(String memberId) {
        List<Point> pointLogs = pointRepository.findAllByMemberId(memberId);

        BigDecimal total = BigDecimal.valueOf(0);
        for (Point point : pointLogs) {
            if (point.getPointType().equals(PointType.BUY))
                total = total.subtract(point.getPointPrice());
            else if (point.getPointType().equals(PointType.SELL))
                total = total.add(point.getPointPrice());
            else if (point.getPointType().equals(PointType.REWARD))
                total = total.add(point.getPointPrice());
        }

        return total;
    }

    @Transactional
    public boolean makePointTransactions(String buyerId, String sellerId, String pointContent, BigDecimal pointPrice) {
        try {
            Point buyerPoint = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.BUY)
                    .transactionDate(LocalDateTime.now())
                    .member(memberRepository.findById(buyerId).orElseThrow())
                    .build();

            Point sellerPoint = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.SELL)
                    .transactionDate(LocalDateTime.now())
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

    @Transactional
    public void givePointReward(String memberId, String pointContent, BigDecimal pointPrice) {
        try {
            Point point = Point.builder()
                    .pointContent(pointContent)
                    .pointPrice(pointPrice)
                    .pointType(PointType.REWARD)
                    .transactionDate(LocalDateTime.now())
                    .member(memberRepository.findById(memberId).orElseThrow())
                    .build();

            pointRepository.save(point);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
