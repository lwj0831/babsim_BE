package likelion.babsim.domain.tag;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_id")
    private Long id;

    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Builder
    public Tag(Recipe recipe, String tagName) {
        this.recipe = recipe;
        this.tagName = tagName;
    }

}
