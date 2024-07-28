package likelion.babsim.web.recipe;


import likelion.babsim.domain.recipe.Difficulty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class RecipeCreateResDto {
    private List<String> recipeImgs;
    private String name;
    private String description;
    private Difficulty difficulty;
    private Integer cookingTime;
    private String categoryName;
    private List<String> tags;
    private List<IngredientForm> ingredients;
    private List<String> recipeContents;
    private List<String> recipeDetailImgs;
    private List<Integer> timers;

    @Builder
    public RecipeCreateResDto(List<String> recipeImgs, String name, String description, Difficulty difficulty, Integer cookingTime, String categoryName, List<String> tags, List<IngredientForm> ingredients, List<String> recipeContents, List<String> recipeDetailImgs, List<Integer> timers) {
        this.recipeImgs = recipeImgs;
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
        this.cookingTime = cookingTime;
        this.categoryName = categoryName;
        this.tags = tags;
        this.ingredients = ingredients;
        this.recipeContents = recipeContents;
        this.recipeDetailImgs = recipeDetailImgs;
        this.timers = timers;
    }
}
