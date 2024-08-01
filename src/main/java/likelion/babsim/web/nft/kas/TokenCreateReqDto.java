package likelion.babsim.web.nft.kas;

import lombok.Getter;

@Getter
public class TokenCreateReqDto {
    String to;
    String id;
    String uri;

    public TokenCreateReqDto(String to, String id, String uri) {
        this.to = to;
        this.id = id;
        this.uri = uri;
    }
}
