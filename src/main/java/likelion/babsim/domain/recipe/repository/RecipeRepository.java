package likelion.babsim.domain.recipe.repository;

import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("SELECT AVG(r.rating) FROM Recipe r JOIN r.reviews rv WHERE r.id = :recipeId")
    Double findAverageRatingByRecipeId(Long recipeId);
}