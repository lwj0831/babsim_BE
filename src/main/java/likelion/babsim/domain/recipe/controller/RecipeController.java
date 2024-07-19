package likelion.babsim.domain.recipe.controller;

import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.recipe.RecipeInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeInfoResDTO> getRecipesByRecipeName(@RequestParam String keyword){
        return recipeService.findRecipesByKeyword(keyword);
    }

    @GetMapping("/api/recipes/week")
    public List<RecipeInfoResDTO> getTop10RecipesByCookedCount(){
        return recipeService.findTop10RecipesByCookedCount();
    }
}
