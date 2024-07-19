package likelion.babsim.domain.recipe.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.RecipeAllergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.cookedRecord.CookedRecord;
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
                    .build();
            entityManager.persist(recipe);

            Tag tag1 = Tag.builder()
                    .recipe(recipe)
                    .tagName("Tag" + i)
                    .build();
            entityManager.persist(tag1);
            Tag tag2 = Tag.builder()
                    .recipe(recipe)
                    .tagName("Tag" + i+1)
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
                    .allergyName("Allergy" + i)
                    .build();
            entityManager.persist(allergy1);
            Allergy allergy2 = Allergy.builder()
                    .allergyName("Allergy" + i)
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
            entityManager.persist(recipeAllergy2);

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
    void findRecipesByKeyword() {
        List<RecipeInfoResDTO> result = recipeService.findRecipesByKeyword("keyword");

        // Then
        assertThat(result).hasSize(10);
        assertThat(result.get(0).getRecipeName()).isEqualTo("keyword_1");
        assertThat(result.get(0).getTags()).contains("Tag1");
        assertThat(result.get(0).getTags()).contains("Tag11");
        assertThat(result.get(0).getRate()).isEqualTo(3);
        assertThat(result.get(0).getAllergies()).hasSize(2);
    }

    @Test
    @Transactional
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
}