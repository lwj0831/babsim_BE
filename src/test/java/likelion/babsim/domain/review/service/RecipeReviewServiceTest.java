package likelion.babsim.domain.review.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import likelion.babsim.web.review.ReviewCreateReqDto;
import likelion.babsim.web.review.ReviewForm;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@SpringBootTest
class RecipeReviewServiceTest {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private RecipeReviewService recipeReviewService;
    @Autowired
    private RecipeReviewRepository recipeReviewRepository;

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

        Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        entityManager.merge(recipe);
        Recipe recipe2 = Recipe.builder()
                .id(2L)
                .build();
        entityManager.merge(recipe2);

        Member member = Member.builder()
                .memberImg("imageUrl1")
                .name("Kim")
                .id("1")
                .build();
        entityManager.merge(member);
        Member member2 = Member.builder()
                .id("2")
                .name("Park")
                .memberImg("Image2")
                .build();
        entityManager.merge(member2);
        log.info("{}",member.getMemberImg());
        log.info("{}",member2.getMemberImg());

        RecipeReview recipeReview = RecipeReview.builder()
                .rating(5)
                .comment("Good!")
                .registerDate(LocalDateTime.now())
                .recipe(recipe)
                .member(member)
                .forkedRecipeId(1L)
                .build();
        entityManager.persist(recipeReview);
        RecipeReview recipeReview2 = RecipeReview.builder()
                .rating(1)
                .comment("Bad")
                .registerDate(LocalDateTime.now())
                .recipe(recipe)
                .member(member2)
                .forkedRecipeId(2L)
                .build();
        entityManager.persist(recipeReview2);
    }
    @Transactional
    @Test
    void findReviewsByRecipeId() {
        List<ReviewForm> reviewForms = recipeReviewService.findReviewsByRecipeId(1L);
        Assertions.assertThat(reviewForms).hasSize(2);
        for (ReviewForm reviewForm : reviewForms) {
            log.info("{}",reviewForm);
        }
    }
    @Transactional
    @Test
    void writeReviews() {
        ReviewCreateReqDto reviewCreateReqDto = ReviewCreateReqDto.builder()
                .comment("Perfect!!")
                .forkedRecipeId(2L)
                .rating(5)
                .build();
        Long l = recipeReviewService.writeReview(1L, "2", reviewCreateReqDto);
        RecipeReview recipeReview = recipeReviewRepository.findById(l).orElseThrow();
        Assertions.assertThat(recipeReview.getRating()).isEqualTo(5);
        Assertions.assertThat(recipeReview.getComment()).isEqualTo("Perfect!!");
        Assertions.assertThat(recipeReview.getForkedRecipeId()).isEqualTo(2L);
    }
}