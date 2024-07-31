package likelion.babsim.web.nft.kas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TokenReadResDto {
    @JsonProperty("cursor")
    private String cursor;
    @JsonProperty("items")
    private List<Item> items;

    @Getter
    public static class Item {
        @JsonProperty("createdAt")
        private long createdAt;
        @JsonProperty("owner")
        private String owner;
        @JsonProperty("previousOwner")
        private String previousOwner;
        @JsonProperty("tokenId")
        private String tokenId;
        @JsonProperty("tokenUri")
        private String tokenUri;
        @JsonProperty("transactionHash")
        private String transactionHash;
        @JsonProperty("updatedAt")
        private long updatedAt;
    }

}
