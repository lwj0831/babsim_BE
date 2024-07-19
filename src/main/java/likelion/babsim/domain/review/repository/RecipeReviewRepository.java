package likelion.babsim.domain.review.repository;

import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.review.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeReviewRepository extends JpaRepository<RecipeReview,Long> {
    List<RecipeReview> findAllByRecipeId(Long recipeId);
}
