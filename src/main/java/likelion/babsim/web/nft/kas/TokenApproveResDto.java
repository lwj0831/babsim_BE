package likelion.babsim.web.nft.kas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TokenApproveResDto {
    @JsonProperty("status")
    String status;
    @JsonProperty("transactionHash")
    String transactionHash;

}
