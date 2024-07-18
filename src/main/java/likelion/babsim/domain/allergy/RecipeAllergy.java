package likelion.babsim.domain.allergy;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecipeAllergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_allergy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="allergy_id")
    private Allergy allergy;
}