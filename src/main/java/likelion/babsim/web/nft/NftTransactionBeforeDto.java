package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NftTransactionBeforeDto {
    private BigDecimal point;
    private BigDecimal nftPrice;
    private boolean available;

    @Builder
    public NftTransactionBeforeDto(BigDecimal point, BigDecimal nftPrice, boolean available) {
        this.point = point;
        this.nftPrice = nftPrice;
        this.available = available;
    }
}
