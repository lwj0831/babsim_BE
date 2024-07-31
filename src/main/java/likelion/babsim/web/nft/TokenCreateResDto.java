package likelion.babsim.web.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenCreateResDto {
    @JsonProperty("status")
    String status;
    @JsonProperty("transactionHash")
    String transactionHash;
}
