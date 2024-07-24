package likelion.babsim.domain.recipe.repository;

import likelion.babsim.domain.recipe.MemberRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRecipeRepository extends JpaRepository<MemberRecipe,Long> {
}
