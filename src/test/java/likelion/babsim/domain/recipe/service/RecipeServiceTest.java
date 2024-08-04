package likelion.babsim.domain.recipe.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.category.Category;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.MemberRecipe;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.tag.Tag;
import likelion.babsim.web.recipe.RecipeInfoResDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class RecipeServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RecipeService recipeService;

    List<Recipe> recipes = new ArrayList<>();

    //@BeforeEach
    void setUp() {
     /*   entityManager.createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeReview").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM Member").executeUpdate();
        entityManager.createQuery("DELETE FROM Likes").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM CookedRecord").executeUpdate();*/

        for (int i = 1; i <= 10; i++) {
            Member member = Member.builder()
                    .id(Integer.toString(i))
                    .name("Test Member"+i)
                    .build();
            entityManager.merge(member);

            Category category = Category.builder()
                    .id(1L)
                    .categoryName("메인요리")
                    .build();
            entityManager.merge(category); //반복문 돌면 이미 존재해서 마지막 recipe와 연결됨
            Category category2 = Category.builder()
                    .id(2L)
                    .categoryName("밑반찬")
                    .build();
            entityManager.merge(category2); //Recipe홀수에 메인요리, 밑반판 카테고리 부여

            Recipe recipe = Recipe.builder()
                    .recipeName("keyword_" + i)
                    .recipeImgs("image" + i + ".jpg")
                    .cookingTime(i * 10)
                    .creatorId("1")
                    .category(category)
                    .build();
            entityManager.persist(recipe);
            Recipe recipe2 = Recipe.builder()
                    .recipeName("keyword2_" + i)
                    .recipeImgs("image" + i + ".jpg")
                    .cookingTime(0)
                    .creatorId("1")
                    .category(category2)
                    .build();
            entityManager.persist(recipe2);

            MemberRecipe memberRecipe = MemberRecipe.builder()
                    .member(member)
                    .recipe(recipe)
                    .build();
            entityManager.persist(memberRecipe);
            MemberRecipe memberRecipe2 = MemberRecipe.builder()
                    .member(member)
                    .recipe(recipe2)
                    .build();
            entityManager.persist(memberRecipe2);//member와 recipe1,recipe2연결

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
        List<RecipeInfoResDto> result = recipeService.findRecipesByKeyword("keyword");

        assertThat(result.get(0).getRecipeName()).isEqualTo("keyword_1");
        assertThat(result.get(0).getTags()).contains("Tag1");
        assertThat(result.get(0).getTags()).contains("Tag21");
        assertThat(result.get(0).getRate()).isEqualTo(3);
        assertThat(result.get(0).getAllergies()).hasSize(2);
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindTop12RecipesByCookedCount() {
        List<RecipeInfoResDto> result = recipeService.findTop12RecipesByCookedCount();

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
        String memberId = "1"; // Assuming the member has ID 1
        List<RecipeInfoResDto> result = recipeService.findRecommendRecipesByMemberId(memberId);

        assertNotNull(result);
        assertEquals(10, result.size()); // Since 10 recipes will be excluded due to the member's allergy

        List<String> excludedRecipes = result.stream()
                .map(RecipeInfoResDto::getRecipeName)
                .toList();

        assertThat(excludedRecipes).doesNotContain("keyword_1");
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindLikesRecipesByMemberId(){
        List<RecipeInfoResDto> result = recipeService.findLikesRecipesByMemberId("1");
        List<String> likesRecipes = result.stream()
                .map(RecipeInfoResDto::getRecipeName)
                .toList();
        assertThat(likesRecipes).hasSize(2);
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindForkedRecipesByMemberId(){
        List<RecipeInfoResDto> result = recipeService.findForkedRecipesByMemberId("2");//Recipe1~20의 creatorId=1L임
        for (RecipeInfoResDto recipeInfoResDTO : result) {
            log.info("{}",recipeInfoResDTO.getRecipeName());
        }
        List<String> forkedRecipes = result.stream()
                .map(RecipeInfoResDto::getRecipeName)
                .toList();
        assertThat(forkedRecipes).hasSize(2);//member(id=2L)과 연관되어 있는 레시피 중(Recipe3,4)에 creatorId가 memberId와 다른거
    }

    @Test
    @Transactional
    @DirtiesContext
    void testFindMyRecipesByOwnerId(){
        List<RecipeInfoResDto> result = recipeService.findMyRecipesByOwnerId("1");
        List<String> myRecipes = result.stream()
                .map(RecipeInfoResDto::getRecipeName)
                .toList();
        assertThat(myRecipes).hasSize(20);
    }
    @Test
    @Transactional
    @DirtiesContext
    void testFindRecipesByCategoryId(){
        List<RecipeInfoResDto> result = recipeService.findRecipesByCategoryId(1L);
        for (RecipeInfoResDto recipeInfoResDto : result) {
            log.info("{}",recipeInfoResDto);
        }
        List<String> recipes = result.stream()
                .map(RecipeInfoResDto::getRecipeName)
                .toList();
        assertThat(recipes).hasSize(12);
    }
}