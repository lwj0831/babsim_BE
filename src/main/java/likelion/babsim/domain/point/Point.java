package likelion.babsim.domain.point;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="point_id")
    private Long id;

    private String pointContent;
    private Integer pointPrice;
    @Enumerated(EnumType.STRING)
    private PointType pointType;
    private LocalDateTime transactionDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name ="member_id")
    private Member member;

    @Builder
    public Point(String pointContent, Integer pointPrice, PointType pointType, LocalDateTime transactionDate, Member member) {
        this.pointContent = pointContent;
        this.pointPrice = pointPrice;
        this.pointType = pointType;
        this.transactionDate = transactionDate;
        this.member = member;
    }
}
