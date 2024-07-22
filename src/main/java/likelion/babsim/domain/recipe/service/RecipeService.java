package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.service.AllergyService;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.domain.formatter.*;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.domain.recipe.*;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.domain.tag.service.TagService;
import likelion.babsim.web.recipe.RecipeDetailResDto;
import likelion.babsim.web.recipe.RecipeInfoResDto;
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

    public List<RecipeInfoResDto> findRecipesByKeyword(String keyword){
        Pageable pageable = PageRequest.of(0, 50);
        List<Recipe> recipes = recipeRepository.findAllByRecipeNameContaining(keyword,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findTop10RecipesByCookedCount(){
        List<Recipe> recipes = cookedRecordService.findTop10CookedRecords().stream()
                .map(CookedRecord::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findRecommendRecipesByMemberId(String memberId){
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> allergies = allergyService.findAllergiesByMemberId(memberId);
        List<Recipe> recipes = recipeRepository.findRecipesExcludingAllergies(allergies,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findLikesRecipesByMemberId(String memberId){
        List<Recipe> recipes = likesService.findLikesByMemberId(memberId).stream()
                .map(Likes::getRecipe)
                .toList();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findForkedRecipesByMemberId(String memberId){
        List<Recipe> recipes = recipeRepository.findAllByMemberIdAndCreatorIdNot(memberId,memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDto> findMyRecipesByCreatorId(String memberId){
        List<Recipe> recipes = recipeRepository.findAllByCreatorId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }
    public List<RecipeInfoResDto> findMyRecipesByOwnerId(String memberId){
        List<Recipe> recipes = recipeRepository.findAllByOwnerId(memberId);
        return recipesToRecipeInfoResDTOList(recipes);
    }
    public List<RecipeInfoResDto> findRecipesByCategoryId(Long categoryId){
        List<Recipe> recipes;
        if(categoryId==0){  //모두보기
            recipes = recipeRepository.findRandom50Recipes();
        }
        else {
            recipes = recipeRepository.findAllByCategoriesId(categoryId);
        }
        return recipesToRecipeInfoResDTOList(recipes);
    }
    public List<RecipeDetailResDto> findRecipeDetailByRecipeIdAndMemberId(Long recipeId, String memberId){
        List<Recipe> recipes = recipeRepository.findById(recipeId).stream().toList();
        return recipesToRecipeDetailResDTOList(recipes,recipeId,memberId);
    }

    private List<RecipeInfoResDto> recipesToRecipeInfoResDTOList(List<Recipe> recipes){
        List<RecipeInfoResDto> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeInfoResDto dto = RecipeInfoResDto.builder()
                    .id(recipe.getId())
                    .recipeImg(recipe.getRecipeImgs())
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
    private List<RecipeDetailResDto> recipesToRecipeDetailResDTOList(List<Recipe> recipes, Long recipeId, String memberId){
        List<RecipeDetailResDto> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeDetailResDto dto = RecipeDetailResDto.builder()
                    .id(recipe.getId())
                    .creatorId(recipe.getCreatorId())
                    .recipeImgs(RecipeImgFormatter.parseImageUrlList(recipe.getRecipeImgs()))
                    .name(recipe.getRecipeName())
                    .description(recipe.getRecipeDescription())
                    .rate(recipeReviewService.findRatingByRecipeId(recipe.getId()))
                    .difficulty(recipe.getDifficulty())
                    .cookingTime(recipe.getCookingTime())
                    .allergies(allergyService.findAllergiesByRecipeId(recipe.getId()))
                    .ingredients(IngredientFormatter.parseIngredientFormList(recipe.getRecipeImgs()))
                    .reviews(recipeReviewService.findReviewsByRecipeId(recipeId))
                    .recipeDetailImgs(RecipeDetailImgFormatter.parseRecipeDetailImgList(recipe.getRecipeDetailImgs()))
                    .recipeContents(RecipeContentFormatter.parseRecipeContentList(recipe.getRecipeContents()))
                    .recipeTimers(RecipeTimerFormatter.parseTimerList(recipe.getTimers()))
                    .liked(likesService.checkLikesByMemberIdAndRecipeId(memberId,recipeId))
                    .build();
            result.add(dto);
        }
        return result;
    }
}
