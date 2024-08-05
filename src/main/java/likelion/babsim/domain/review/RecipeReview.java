package likelion.babsim.domain.review;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_review_id")
    private Long id;

    private Integer rating;
    private String comment;
    private LocalDateTime registerDate;

    private Long forkRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public RecipeReview(Integer rating, String comment, LocalDateTime registerDate, Long forkRecipeId, Recipe recipe, Member member) {
        this.rating = rating;
        this.comment = comment;
        this.registerDate = registerDate;
        this.forkRecipeId = forkRecipeId;
        this.recipe = recipe;
        this.member = member;
    }
}