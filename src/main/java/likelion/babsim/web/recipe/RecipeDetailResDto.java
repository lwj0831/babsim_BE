package likelion.babsim.web.recipe;

import likelion.babsim.domain.recipe.Difficulty;
import likelion.babsim.web.review.ReviewForm;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeDetailResDto {

    private Long id;
    private String creatorId;
    private List<String> recipeImgs;
    private String name;
    private String description;
    private Double rate;
    private Difficulty difficulty;
    private Integer cookingTime;
    private List<String> tags;
    private List<Long> allergies;
    private List<IngredientForm> ingredients;
    private List<ReviewForm> reviews;
    private Long reviewsCount;
    private List<String> recipeDetailImgs;
    private List<String> recipeContents;
    private List<Integer> recipeTimers;
    private boolean liked;

    @Builder
    public RecipeDetailResDto(Long id, String creatorId, List<String> recipeImgs, String name, String description, Double rate, Difficulty difficulty, Integer cookingTime, List<String> tags, List<Long> allergies, List<IngredientForm> ingredients, List<ReviewForm> reviews,Long reviewsCount, List<String> recipeDetailImgs, List<String> recipeContents, List<Integer> recipeTimers, boolean liked) {
        this.id = id;
        this.creatorId = creatorId;
        this.recipeImgs = recipeImgs;
        this.name = name;
        this.description = description;
        this.rate = rate;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.tags = tags;
        this.allergies = allergies;
        this.ingredients = ingredients;
        this.reviews = reviews;
        this.reviewsCount = reviewsCount;
        this.recipeDetailImgs = recipeDetailImgs;
        this.recipeContents = recipeContents;
        this.recipeTimers = recipeTimers;
        this.liked = liked;
    }
}
