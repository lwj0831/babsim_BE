package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NftApproveResDto {
    private String ownerName;
    private String toName;
    private String tokenId;

    @Builder
    public NftApproveResDto(String ownerName, String toName, String tokenId) {
        this.ownerName = ownerName;
        this.toName = toName;
        this.tokenId = tokenId;
    }
}
