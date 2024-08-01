package likelion.babsim.domain.nft;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Builder
    public Nft(String tokenId, String uri, String ownerId, Recipe recipe) {
        this.tokenId = tokenId;
        this.uri = uri;
        this.ownerId = ownerId;
        this.recipe = recipe;
    }
}
