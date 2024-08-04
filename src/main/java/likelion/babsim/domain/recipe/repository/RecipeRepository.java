package likelion.babsim.domain.recipe.repository;

import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findAllByRecipeNameContaining(String keyword);
    @Query("SELECT r FROM Recipe r WHERE r.id NOT IN (SELECT ra.recipe.id FROM RecipeAllergy ra WHERE ra.allergy.id IN :allergyIds)")
    List<Recipe> findRecipesExcludingAllergies(@Param("allergyIds") List<Long> allergyIds, Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.creatorId <> :memberId AND EXISTS (SELECT mr FROM MemberRecipe mr WHERE mr.recipe = r AND mr.member.id = :memberId)")
    List<Recipe> findByCreatorIdNotAndMemberId(@Param("memberId") String memberId);
    @Query("SELECT r FROM Recipe r WHERE r.creatorId <> :memberId AND r.id = :recipeId AND EXISTS (SELECT mr FROM MemberRecipe mr WHERE mr.recipe = r AND mr.member.id = :memberId)")
    List<Recipe> findByCreatorIdNotAndMemberIdAndRecipeId(@Param("memberId") String memberId, @Param("recipeId") Long recipeId);

    List<Recipe> findAllByCreatorId(String creatorId);
    Optional<Recipe> findByNft(Nft nft);
    List<Recipe> findAllByCategoryId(Long categoryId);

    default List<Recipe> findRandom50Recipes() {
        Pageable pageable = PageRequest.of(0, 50);
        Page<Recipe> page = findAll(pageable);
        return page.getContent();
    }

}
