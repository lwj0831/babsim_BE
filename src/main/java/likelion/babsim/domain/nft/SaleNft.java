package likelion.babsim.domain.nft;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleNft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_nft_id")
    private Long id;

    private BigDecimal price;
    private LocalDateTime saleStartTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nft_id")
    private Nft nft;

    @Builder
    public SaleNft(BigDecimal price, LocalDateTime saleStartTime, Nft nft) {
        this.price = price;
        this.saleStartTime = saleStartTime;
        this.nft = nft;
    }
}

