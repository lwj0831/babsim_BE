package likelion.babsim.domain.recipe.controller;

import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.recipe.RecipeCreateReqDto;
import likelion.babsim.web.recipe.RecipeCreateResDto;
import likelion.babsim.web.recipe.RecipeDetailResDto;
import likelion.babsim.web.recipe.RecipeInfoResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recipes")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeInfoResDto> getRecipesByRecipeName(@RequestParam("keyword") String keyword){
        return recipeService.findRecipesByKeyword(keyword);
    }

    @GetMapping("/week")
    public List<RecipeInfoResDto> getWeeklyBestRecipesByCookedCount(){
        return recipeService.findWeeklyBestRecipesByCookedCount();
    }

    @GetMapping("/recommend")
    public List<RecipeInfoResDto> getRecommendRecipesByMemberId(@RequestParam("memberId") String memberId){
        return recipeService.findRecommendRecipesByMemberId(memberId);
    }

    @GetMapping("/likes")
    public List<RecipeInfoResDto> getLikesRecipesByMemberId(@RequestParam("memberId") String memberId){
        return recipeService.findLikesRecipesByMemberId(memberId);
    }

    @GetMapping("/forked")
    public List<RecipeInfoResDto> getForkedRecipesByMemberId(@RequestParam("memberId") String memberId){
        return recipeService.findForkedRecipesByMemberId(memberId);
    }
    @GetMapping("/forked/{forkedRecipeId}")
    public List<RecipeInfoResDto> getSpecificForkedRecipesByMemberIdAndForkedRecipeId(@RequestParam("memberId") String memberId,@PathVariable("forkedRecipeId") Long forkedRecipeId){
        return recipeService.findSpecificForkedRecipesByMemberIdAndForkedRecipeId(memberId,forkedRecipeId);
    }

    @GetMapping("/own")
    public List<RecipeInfoResDto> getMyRecipesByOwnerId(@RequestParam("memberId") String memberId){
        return recipeService.findMyRecipesByOwnerId(memberId);
    }
    @GetMapping("/category/{categoryId}")
    public List<RecipeInfoResDto> getRecipesByCategoryId(@PathVariable("categoryId") Long categoryId){
        return recipeService.findRecipesByCategoryId(categoryId);
    }

    @GetMapping("/{recipeId}")
    public RecipeDetailResDto getRecipeDetailByRecipeIdAndMemberId(@PathVariable("recipeId") Long recipeId, @RequestParam(value="memberId", required = false,defaultValue = "null") String memberId){
        return recipeService.findRecipeDetailByRecipeIdAndMemberId(recipeId,memberId);
    }

    @PostMapping(produces = "application/json")
    public RecipeCreateResDto createRecipe(@RequestBody RecipeCreateReqDto dto, @RequestParam("creatorId") String creatorId){
        return recipeService.createRecipe(dto,creatorId);
    }
    @PutMapping("/{recipeId}")
    public RecipeCreateResDto editRecipe(@RequestBody RecipeCreateReqDto dto, @PathVariable("recipeId") Long recipeId){
        return recipeService.editRecipe(dto,recipeId);
    }

    @PostMapping("/{recipeId}/fork")
    public RecipeCreateResDto forkRecipe(@RequestBody RecipeCreateReqDto dto, @RequestParam("creatorId") String creatorId, @PathVariable("recipeId") Long recipeId){
        return recipeService.forkRecipe(dto,creatorId,recipeId);
    }

}
