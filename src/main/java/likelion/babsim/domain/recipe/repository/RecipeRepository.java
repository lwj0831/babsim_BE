package likelion.babsim.domain.recipe.repository;

import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findAllByRecipeNameContaining(String keyword, Pageable pageable);
}
