package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.service.AllergyService;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.domain.tag.service.TagService;
import likelion.babsim.web.recipe.RecipeDetailResDTO;
import likelion.babsim.web.recipe.RecipeInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final TagService tagService;
    private final RecipeReviewService recipeReviewService;
    private final AllergyService allergyService;
    private final CookedRecordService cookedRecordService;
    private final LikesService likesService;

    public List<RecipeInfoResDTO> findRecipesByKeyword(String keyword){
        Pageable pageable = PageRequest.of(0, 50);
        List<Recipe> recipes = recipeRepository.findAllByRecipeNameContaining(keyword,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findTop10RecipesByCookedCount(){
        List<Recipe> recipes = cookedRecordService.findTop10CookedRecords().stream()
                .map(CookedRecord::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findRecommendRecipesByMemberId(Long memberId){
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> allergies = allergyService.findAllergiesByMemberId(memberId);
        List<Recipe> recipes = recipeRepository.findRecipesExcludingAllergies(allergies,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findLikesRecipesByMemberId(Long memberId){
        List<Recipe> recipes = likesService.findLikesByMemberId(memberId).stream()
                .map(Likes::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findForkedRecipesByMemberId(Long memberId){
        List<Recipe> recipes = recipeRepository.findAllByMemberIdAndCreatorIdNot(memberId,memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findMyRecipesByCreatorId(Long memberId){
        List<Recipe> recipes = recipeRepository.findAllByCreatorId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }
    public List<RecipeInfoResDTO> findMyRecipesByOwnerId(Long memberId){
        List<Recipe> recipes = recipeRepository.findAllByOwnerId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    private List<RecipeInfoResDTO> recipesToRecipeInfoResDTOList(List<Recipe> recipes){
        List<RecipeInfoResDTO> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeInfoResDTO dto = RecipeInfoResDTO.builder()
                    .id(recipe.getId())
                    .recipeImg(recipe.getRecipeImg())
                    .recipeName(recipe.getRecipeName())
                    .cookingTime(recipe.getCookingTime())
                    .tags(tagService.findTagNamesByRecipeId(recipe.getId()))
                    .rate(recipeReviewService.findRatingByRecipeId(recipe.getId()))
                    .allergies(allergyService.findAllergiesByRecipeId(recipe.getId()))
                    .build();
            result.add(dto);
        }
        return result;
    }
    private List<RecipeDetailResDTO> recipesToRecipeDetailResDTOList(List<Recipe> recipes, Long memberId){
        List<RecipeDetailResDTO> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDetailResDTO dto = RecipeDetailResDTO.builder()
                    .id(recipe.getId())
                    .recipeImg(recipe.getRecipeImg())
                    .recipeName(recipe.getRecipeName())
                    .cookingTime(recipe.getCookingTime())
                    .tags(tagService.findTagNamesByRecipeId(recipe.getId()))
                    .rate(recipeReviewService.findRatingByRecipeId(recipe.getId()))
                    .allergies(allergyService.findAllergiesByRecipeId(recipe.getId()))
                    .liked(likesService.checkLikesByMemberIdAndRecipeId(memberId,recipe.getId()))
                    .build();
            result.add(dto);
        }
        return result;
    }
}
