package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SaleNftRegisterResDto {
    private Long saleNftId;
    private String status;

    @Builder
    public SaleNftRegisterResDto(Long saleNftId, String status) {
        this.saleNftId = saleNftId;
        this.status = status;
    }
}
