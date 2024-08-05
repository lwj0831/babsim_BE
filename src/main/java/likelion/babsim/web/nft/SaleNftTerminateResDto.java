package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaleNftTerminateResDto {
    private Long saleNftId;
    private String status;

    @Builder
    public SaleNftTerminateResDto(Long saleNftId, String status) {
        this.saleNftId = saleNftId;
        this.status = status;
    }
}
