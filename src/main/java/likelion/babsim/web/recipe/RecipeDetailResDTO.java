package likelion.babsim.web.recipe;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class RecipeDetailResDTO {

    private Long id;
    private String recipeImg;
    private String recipeName;
    private List<String> tags;
    private Integer cookingTime;
    private Double rate;
    private List<Long> allergies;
    private boolean liked;

    @Builder
    public RecipeDetailResDTO(Long id, String recipeImg, String recipeName, List<String> tags, Integer cookingTime, Double rate, List<Long> allergies, boolean liked) {
        this.id = id;
        this.recipeImg = recipeImg;
        this.recipeName = recipeName;
        this.tags = tags;
        this.cookingTime = cookingTime;
        this.rate = rate;
        this.allergies = allergies;
        this.liked = liked;
    }
}
