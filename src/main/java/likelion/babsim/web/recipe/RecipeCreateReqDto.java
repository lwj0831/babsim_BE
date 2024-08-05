package likelion.babsim.web.recipe;

import likelion.babsim.domain.recipe.Difficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class RecipeCreateReqDto {
    private List<String> recipeImgs;
    private String name;
    private String description;
    private Difficulty difficulty;
    private Integer cookingTime;
    private Long categoryId;
    private List<String> tags;
    private List<IngredientDTO> ingredients;
    private List<String> recipeContents;
    private List<String> recipeDetailImgs;
    private List<Integer> timers;

    @Getter
    public static class IngredientDTO {
        private String name;
        private String amount;
    }

    @Builder
    public RecipeCreateReqDto(List<String> recipeImgs, String name, String description, Difficulty difficulty, Integer cookingTime,Long categoryId, List<String> tags, List<IngredientDTO> ingredients, List<String> recipeContents, List<String> recipeDetailImgs, List<Integer> timers) {
        this.recipeImgs = recipeImgs;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.categoryId = categoryId;
        this.tags = tags;
        this.ingredients = ingredients;
        this.recipeContents = recipeContents;
        this.recipeDetailImgs = recipeDetailImgs;
        this.timers = timers;
    }
}
