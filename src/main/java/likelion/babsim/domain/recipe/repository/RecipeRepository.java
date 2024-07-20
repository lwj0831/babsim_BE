package likelion.babsim.domain.recipe.repository;

import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe,Long> {
    List<Recipe> findAllByRecipeNameContaining(String keyword, Pageable pageable);
    @Query("SELECT r FROM Recipe r WHERE r.id NOT IN (SELECT ra.recipe.id FROM RecipeAllergy ra WHERE ra.allergy.id IN :allergyIds)")
    List<Recipe> findRecipesExcludingAllergies(@Param("allergyIds") List<Long> allergyIds, Pageable pageable);

    List<Recipe> findAllByMemberIdAndCreatorIdNot(String memberId,String creatorId);
    /*@Query("SELECT r FROM Recipe r WHERE r.memberId = :memberId AND r.forked = :forked")
    List<Recipe> findRecipesByMemberIdAndForked(@Param("memberId") Long memberId, @Param("forked") boolean forked);*/
    List<Recipe> findAllByCreatorId(String creatorId);
    List<Recipe> findAllByOwnerId(String ownerId);


}
