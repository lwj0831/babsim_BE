package likelion.babsim.web.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenApproveReqDto {
    @JsonProperty("sender")
    private String sender;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("to")
    private String to;

    public TokenApproveReqDto(String sender, String owner, String to) {
        this.sender = sender;
        this.owner = owner;
        this.to = to;
    }
}
