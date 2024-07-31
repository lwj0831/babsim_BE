package likelion.babsim.web.nft.kas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AccountCreateResDto {

    @JsonProperty("address")
    private String address;

    @JsonProperty("chainId")
    private Integer chainId;

    @JsonProperty("createdAt")
    private Long createdAt;

    @JsonProperty("keyId")
    private String keyId;

    @JsonProperty("krn")
    private String krn;

    @JsonProperty("publicKey")
    private String publicKey;

    @JsonProperty("updatedAt")
    private Long updatedAt;

}

