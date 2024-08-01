package likelion.babsim.web.point;

import likelion.babsim.domain.point.PointType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PointLogResDTO {
    private Long id;
    private String pointContent;
    private Integer pointPrice;
    private PointType pointType;

    @Builder
    public PointLogResDTO(Long id, String pointContent, Integer pointPrice, PointType pointType) {
        this.id = id;
        this.pointContent = pointContent;
        this.pointPrice = pointPrice;
        this.pointType = pointType;
    }
}
