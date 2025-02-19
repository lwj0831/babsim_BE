package likelion.babsim.web.nft;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NftInfoResDto {

    private Long nftId;
    private String uri;
    private Long recipeId;
    private String recipeName;
    private BigDecimal price;

    private boolean nftSaleStatus;

    @Builder
    public NftInfoResDto(Long nftId, String uri, Long recipeId, String recipeName, BigDecimal price, boolean nftSaleStatus) {
        this.nftId = nftId;
        this.uri = uri;
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.price = price;
        this.nftSaleStatus = nftSaleStatus;
    }
}


