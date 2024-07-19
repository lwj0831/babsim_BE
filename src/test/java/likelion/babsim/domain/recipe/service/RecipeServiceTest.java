package likelion.babsim.domain.recipe.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import likelion.babsim.domain.tag.Tag;
import likelion.babsim.domain.tag.repository.TagRepository;
import likelion.babsim.web.recipe.RecipeInfoResDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RecipeService recipeService;

    List<Recipe> recipes = new ArrayList<>();

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeReview").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeAllergy").executeUpdate();

        for (int i = 1; i <= 10; i++) {
            Recipe recipe = Recipe.builder()
                    .recipeName("keyword_" + i)
                    .recipeImg("image" + i + ".jpg")
                    .cookingTime(i * 10)
                    .forked(true)
                    .build();
            entityManager.persist(recipe);
            Recipe recipe2 = Recipe.builder()
                    .recipeName("keyword2_" + i)
                    .recipeImg("image" + i + ".jpg")
                    .cookingTime(0)
                    .forked(true)
                    .build();
            entityManager.persist(recipe2);

            Tag tag1 = Tag.builder()
                    .recipe(recipe)
                    .tagName("Tag" + i)
                    .build();
            entityManager.persist(tag1);
            Tag tag2 = Tag.builder()
                    .recipe(recipe)
                    .tagName("Tag2" + i)
                    .build();
            entityManager.persist(tag2);

            RecipeReview review1 = RecipeReview.builder()
                    .recipe(recipe)
                    .rating(2)
                    .build();
            entityManager.persist(review1);
            RecipeReview review2 = RecipeReview.builder()
                    .recipe(recipe)
                    .rating(4)
                    .build();
            entityManager.persist(review2);

            Allergy allergy1 = Allergy.builder()
                    .allergyName("Milk Allergy")
                    .build();
            entityManager.persist(allergy1);
            Allergy allergy2 = Allergy.builder()
                    .allergyName("Peanut Allergy")
                    .build();
            entityManager.persist(allergy2);

            RecipeAllergy recipeAllergy1 = RecipeAllergy.builder()
                    .recipe(recipe)
                    .allergy(allergy1)
                    .build();
            entityManager.persist(recipeAllergy1);
            RecipeAllergy recipeAllergy2 = RecipeAllergy.builder()
                    .recipe(recipe)
                    .allergy(allergy2)
                    .build();
            entityManager.persist(recipeAllergy2); //recipe에 allergy1,allergy2 부여

            Member member = Member.builder()
                    .id((long)i)
                    .name("Test Member"+i)
                    .build();
            entityManager.merge(member);

            Likes like = Likes.builder()
                    .member(member)
                    .recipe(recipe).
                    build();
            entityManager.persist(like);
            Likes like2 = Likes.builder()
                    .member(member)
                    .recipe(recipe2).
                    build();
            entityManager.persist(like2);

            MemberAllergy memberAllergy = MemberAllergy.builder()
                    .member(member)
                    .allergy(allergy1)
                    .build();
            entityManager.persist(memberAllergy); //member는 allergy1있음

            CookedRecord cookedRecord = CookedRecord.builder()
                    .recipe(recipe)
                    .cookedCount((long) (i * 10))
                    .build();
            entityManager.persist(cookedRecord);

            recipes.add(recipe);
        }
        entityManager.flush();
        entityManager.clear();
    }
    @Test
    @Transactional
    @DirtiesContext
    void findRecipesByKeyword() {
        List<RecipeInfoResDTO> result = recipeService.findRecipesByKeyword("keyword");

        assertThat(result.get(0).getRecipeName()).isEqualTo("keyword_1");
        assertThat(result.get(0).getTags()).contains("Tag1");
        assertThat(result.get(0).getTags()).contains("Tag21");
        assertThat(result.get(0).getRate()).isEqualTo(3);
        assertThat(result.get(0).getAllergies()).hasSize(2);
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindTop10RecipesByCookedCount() {
        List<RecipeInfoResDTO> result = recipeService.findTop10RecipesByCookedCount();

        assertNotNull(result);
        assertEquals(10, result.size()); // Verify we get 10 results

        List<Long> expectedIds = recipes.stream()
                .sorted((r1, r2) -> Long.compare(r2.getId(), r1.getId())) // Sort by ID, assuming higher ID means higher cookedCount
                .limit(10)
                .map(Recipe::getId)
                .toList();

        for (int i = 0; i < result.size(); i++) {
            assertEquals(expectedIds.get(i), result.get(i).getId());
        }
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindRecommendRecipesByMemberId() {
        Long memberId = 1L; // Assuming the member has ID 1
        List<RecipeInfoResDTO> result = recipeService.findRecommendRecipesByMemberId(memberId);

        assertNotNull(result);
        assertEquals(10, result.size()); // Since 10 recipes will be excluded due to the member's allergy

        List<String> excludedRecipes = result.stream()
                .map(RecipeInfoResDTO::getRecipeName)
                .toList();

        assertThat(excludedRecipes).doesNotContain("keyword_1");
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindLikesRecipesByMemberId(){
        List<RecipeInfoResDTO> result = recipeService.findLikesRecipesByMemberId(1L);
        List<String> likesRecipes = result.stream()
                .map(RecipeInfoResDTO::getRecipeName)
                .toList();
        assertThat(likesRecipes).hasSize(2);
    }

    @Test
    @Transactional
    void testFindForkedRecipesByMemberId(){
        List<RecipeInfoResDTO> result = recipeService.findForkedRecipesByMemberId(1L);
        List<String> forkedRecipes = result.stream()
                .map(RecipeInfoResDTO::getRecipeName)
                .toList();
        assertThat(forkedRecipes).hasSize(2);
    }
}