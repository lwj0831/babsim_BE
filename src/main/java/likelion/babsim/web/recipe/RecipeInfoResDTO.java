package likelion.babsim.web.recipe;

import likelion.babsim.domain.recipe.Difficulty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeInfoResDTO {

    private Long id;
    private String recipeImg;
    private String recipeName;
    private List<String> tags;
    private Integer cookingTime;
    private Double rate;
    private List<Long> allergies;

    @Builder
    public RecipeInfoResDTO(Long id, String recipeImg, String recipeName, List<String> tags, Integer cookingTime, Double rate, List<Long> allergies) {
        this.id = id;
        this.recipeImg = recipeImg;
        this.recipeName = recipeName;
        this.tags = tags;
        this.cookingTime = cookingTime;
        this.rate = rate;
        this.allergies = allergies;
    }
}
