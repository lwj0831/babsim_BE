package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.allergy.service.MemberAllergyService;
import likelion.babsim.domain.allergy.service.RecipeAllergyService;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.repository.CookedRecordRepository;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.domain.tag.repository.TagRepository;
import likelion.babsim.domain.tag.Tag;
import likelion.babsim.domain.tag.service.TagService;
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
    private final RecipeAllergyService recipeAllergyService;
    private final CookedRecordService cookedRecordService;
    private final MemberAllergyService memberAllergyService;

    public List<RecipeInfoResDTO> findRecipesByKeyword(String keyword){
        Pageable pageable = PageRequest.of(0, 50);
        List<Recipe> recipes = recipeRepository.findAllByRecipeNameContaining(keyword,pageable);
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findTop10RecipesByCookedCount(){
        List<Recipe> recipes = cookedRecordService.findTop10Recipes();
        return recipesToRecipeInfoResDTOList(recipes);
    }

    public List<RecipeInfoResDTO> findRecommendRecipes(Long memberId){
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> allergies = memberAllergyService.findAllergiesByMemberId(memberId);
        List<Recipe> recipes = recipeRepository.findRecipesExcludingAllergies(allergies,pageable);
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
                    .allergies(recipeAllergyService.findAllergiesByRecipeId(recipe.getId()))
                    .build();
            result.add(dto);
        }
        return result;
    }
}
