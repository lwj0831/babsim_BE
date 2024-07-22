package likelion.babsim.domain.likes.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.likes.LikesUpdateResDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class LikesServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private LikesService likesService;

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeReview").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM Member").executeUpdate();
        entityManager.createQuery("DELETE FROM Likes").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM CookedRecord").executeUpdate();

        Member member = Member.builder()
                .id("1")
                .build();
        entityManager.merge(member);
        Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        entityManager.merge(recipe);
        Recipe recipe2 = Recipe.builder()
                .id(2L)
                .build();
        entityManager.merge(recipe2);

        Likes likes = Likes.builder()
                .member(member)
                .recipe(recipe)
                .build();
        entityManager.persist(likes);
    }
    @Test
    @Transactional
    void checkLikesByMemberIdAndRecipeId() {
        boolean result = likesService.checkLikesByMemberIdAndRecipeId("1", 1L);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    @Transactional
    void updateLikesByMemberIdAndRecipeId() {
        LikesUpdateResDto likesUpdateResDto = likesService.updateLikesByMemberIdAndRecipeId("1", 1L);
        Assertions.assertThat(likesUpdateResDto.getStatus()).isEqualTo("deleted");
        LikesUpdateResDto likesUpdateResDto2 = likesService.updateLikesByMemberIdAndRecipeId("1", 2L);
        Assertions.assertThat(likesUpdateResDto2.getStatus()).isEqualTo("created");
    }
}