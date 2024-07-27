package likelion.babsim.domain.recipe.controller;

import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.recipe.RecipeCreateReqDto;
import likelion.babsim.web.recipe.RecipeDetailResDto;
import likelion.babsim.web.recipe.RecipeInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@CrossOrigin(origins = {"http://localhost:5173"})
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeInfoResDto> getRecipesByRecipeName(@RequestParam String keyword){
        return recipeService.findRecipesByKeyword(keyword);
    }

    @GetMapping("/week")
    public List<RecipeInfoResDto> getTop10RecipesByCookedCount(){
        return recipeService.findTop10RecipesByCookedCount();
    }

    @GetMapping("/recommend")
    public List<RecipeInfoResDto> getRecommendRecipesByMemberId(@RequestParam String memberId){
        return recipeService.findRecommendRecipesByMemberId(memberId);
    }

    @GetMapping("/likes")
    public List<RecipeInfoResDto> getLikesRecipesByMemberId(@RequestParam String memberId){
        return recipeService.findLikesRecipesByMemberId(memberId);
    }

    @GetMapping("/forked")
    public List<RecipeInfoResDto> getForkedRecipesByMemberId(@RequestParam String memberId){
        return recipeService.findForkedRecipesByMemberId(memberId);
    }
    @GetMapping("/forked/{recipeId}")
    public List<RecipeInfoResDto> getSpecificForkedRecipesByMemberIdAndRecipeId(@RequestParam String memberId,@PathVariable Long recipeId){
        return recipeService.findSpecificForkedRecipesByMemberIdAndRecipeId(memberId,recipeId);
    }

    @GetMapping("/own")
    public List<RecipeInfoResDto> getMyRecipesByOwnerId(@RequestParam String memberId){
        return recipeService.findMyRecipesByOwnerId(memberId);
    }
    @GetMapping("/category/{categoryId}")
    public List<RecipeInfoResDto> getRecipesByCategoryId(@PathVariable Long categoryId){
        return recipeService.findRecipesByCategoryId(categoryId);
    }

    @GetMapping("/{recipeId}")
    public RecipeDetailResDto getRecipeDetailByRecipeIdAndMemberId(@PathVariable Long recipeId, @RequestParam(required = false,defaultValue = "null") String memberId){
        return recipeService.findRecipeDetailByRecipeIdAndMemberId(recipeId,memberId);
    }

    @PostMapping
    public Recipe createRecipe(@RequestBody RecipeCreateReqDto dto, @RequestParam String creatorId){
        return recipeService.createRecipe(dto,creatorId);
    }
    @PostMapping("/{recipeId}")
    public Recipe editRecipe(@RequestBody RecipeCreateReqDto dto, @RequestParam String creatorId, @PathVariable Long recipeId){
        return recipeService.editRecipe(dto,creatorId,recipeId);
    }

}
