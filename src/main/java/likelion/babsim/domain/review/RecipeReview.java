package likelion.babsim.domain.review;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class RecipeReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="recipe_review_id")
    private Long id;

    private String title;
    private int rating;
    private String comment;
    private LocalDateTime registerDate;

    private Long forkedRecipeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}