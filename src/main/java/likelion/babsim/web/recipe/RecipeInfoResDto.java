package likelion.babsim.web.recipe;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeInfoResDto {

    private Long id;
    private String recipeImg;
    private String recipeName;
    private List<String> tags;
    private Integer cookingTime;
    private Double rate;
    private List<Long> allergies;

    @Builder
    public RecipeInfoResDto(Long id, String recipeImg, String recipeName, List<String> tags, Integer cookingTime, Double rate, List<Long> allergies) {
        this.id = id;
        this.recipeImg = recipeImg;
        this.recipeName = recipeName;
        this.tags = tags;
        this.cookingTime = cookingTime;
        this.rate = rate;
        this.allergies = allergies;
    }
}
