package likelion.babsim.domain.recipe.controller;

import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.recipe.RecipeInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/week")
    public List<RecipeInfoResDTO> getTop10RecipesByCookedCount(){
        return recipeService.findTop10RecipesByCookedCount();
    }

    @GetMapping("/recommend")
    public List<RecipeInfoResDTO> getRecommendRecipesByMemberId(@RequestParam Long memberId){
        return recipeService.findRecommendRecipesByMemberId(memberId);
    }

    @GetMapping("/likes")
    public List<RecipeInfoResDTO> getLikesRecipesByMemberId(@RequestParam Long memberId){
        return recipeService.findLikesRecipesByMemberId(memberId);
    }

    @GetMapping("/forked")
    public List<RecipeInfoResDTO> getForkedRecipesByMemberId(@RequestParam Long memberId){
        return recipeService.findForkedRecipesByMemberId(memberId);
    }

    @GetMapping("/{memberId}")
    public List<RecipeInfoResDTO> getMyRecipesByOwnerId(@PathVariable Long memberId){
        return recipeService.findMyRecipesByOwnerId(memberId);
    }
}
