package likelion.babsim.domain.recipe.service;

import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import likelion.babsim.domain.tag.repository.TagRepository;
import likelion.babsim.domain.tag.Tag;
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
    private final TagRepository tagRepository;
    private final RecipeReviewRepository recipeReviewRepository;
    private final RecipeAllergyRepository recipeAllergyRepository;

    public List<RecipeInfoResDTO> findRecipesByKeyword(String keyword){
        Pageable pageable = PageRequest.of(0, 50);
        List<Recipe> recipes = recipeRepository.findAllByRecipeNameContaining(keyword,pageable);
        List<RecipeInfoResDTO> result = new ArrayList<>();
        for (Recipe recipe : recipes) {
            RecipeInfoResDTO dto = RecipeInfoResDTO.builder()
                    .id(recipe.getId())
                    .recipeImg(recipe.getRecipeImg())
                    .recipeName(recipe.getRecipeName())
                    .cookingTime(recipe.getCookingTime())
                    .tags(tagRepository.findAllByRecipeId(recipe.getId()).stream()
                            .map(Tag::getTagName)
                            .toList())
                    .rate(recipeReviewRepository.findAllByRecipeId(recipe.getId()).stream()
                            .map(RecipeReview::getRating)
                            .mapToDouble(r->r)
                            .average()
                            .orElse(0.0))
                    .allergies(recipeAllergyRepository.findAllByRecipeId(recipe.getId()).stream()
                            .map(r->r.getAllergy().getId())
                            .toList())
                    .build();
            result.add(dto);
        }
        return result;
    }
}
