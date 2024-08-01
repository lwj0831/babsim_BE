package likelion.babsim.web.point;

import likelion.babsim.domain.point.PointType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PointLogResDTO {
    private Long id;
    private String pointContent;
    private Integer pointPrice;
    private PointType pointType;
    private LocalDateTime transactionDate;

    @Builder
    public PointLogResDTO(Long id, String pointContent, Integer pointPrice, PointType pointType, LocalDateTime transactionDate) {
        this.id = id;
        this.pointContent = pointContent;
        this.pointPrice = pointPrice;
        this.pointType = pointType;
        this.transactionDate = transactionDate;
    }
}
