package likelion.babsim.web.nft;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class AccountReadResDto {

    @JsonProperty("cursor")
    private String cursor;
    @JsonProperty("items")
    private List<Item> items;

    @Getter
    public static class Item {
        @JsonProperty("address")
        private String address;
        @JsonProperty("chaidId")
        private int chainId;
        @JsonProperty("createdAt")
        private long createdAt;
        @JsonProperty("keyId")
        private String keyId;
        @JsonProperty("krn")
        private String krn;
        @JsonProperty("publicKey")
        private String publicKey;
        @JsonProperty("updatedAt")
        private long updatedAt;
    }
}

