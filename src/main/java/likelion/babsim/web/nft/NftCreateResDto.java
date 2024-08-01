package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

@Getter
public class NftCreateResDto {
    private Long id;
    private String tokenId;
    private String uri;

    @Builder
    public NftCreateResDto(Long id, String tokenId, String uri) {
        this.id = id;
        this.tokenId = tokenId;
        this.uri = uri;
    }
}
