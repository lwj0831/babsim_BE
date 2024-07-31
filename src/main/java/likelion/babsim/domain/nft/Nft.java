package likelion.babsim.domain.nft;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.sale.Sale;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nft_id")
    private Long id;
    private String tokenId;
    private String uri;
    private String ownerId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @OneToOne(mappedBy = "nft", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Sale sale;

    @Builder
    public Nft(String tokenId, String uri, String ownerId, Recipe recipe) {
        this.tokenId = tokenId;
        this.uri = uri;
        this.ownerId = ownerId;
        this.recipe = recipe;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
