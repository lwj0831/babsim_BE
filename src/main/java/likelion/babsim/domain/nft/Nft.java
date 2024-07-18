package likelion.babsim.domain.nft;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="nft_id")
    private Long id;
    private String content;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;
}
