package likelion.babsim.web.nft.kas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TokenCreateResDto {
    @JsonProperty("status")
    String status;
    @JsonProperty("transactionHash")
    String transactionHash;
}
