package likelion.babsim.domain.sale;

import jakarta.persistence.*;
import likelion.babsim.domain.nft.Nft;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    private BigDecimal price; // 판매 가격
    private LocalDateTime saleStartTime; // 판매 시작 시간
    private LocalDateTime saleEndTime; // 판매 종료 시간
    private SaleStatus status; // 판매 상태

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id")
    private Nft nft;

}

