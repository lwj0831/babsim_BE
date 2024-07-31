package likelion.babsim.domain.allergy.repository;

import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeAllergyRepository extends JpaRepository<RecipeAllergy,Long> {
    List<RecipeAllergy> findAllByRecipeId(Long recipeId);

    void deleteByRecipe(Recipe recipe);
}
