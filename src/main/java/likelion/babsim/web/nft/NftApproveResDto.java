package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NftApproveResDto {
    private String ownerAddress;
    private String toAddress;
    private String tokenId;

    @Builder
    public NftApproveResDto(String ownerAddress, String toAddress, String tokenId) {
        this.ownerAddress = ownerAddress;
        this.toAddress = toAddress;
        this.tokenId = tokenId;
    }
}
