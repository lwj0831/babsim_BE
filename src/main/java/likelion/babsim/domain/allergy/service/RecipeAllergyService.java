package likelion.babsim.domain.allergy.service;

import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import likelion.babsim.domain.recipe.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeAllergyService {
    private final RecipeAllergyRepository recipeAllergyRepository;

    public List<Long> findAllergiesByRecipeId(Long recipeId){
        return recipeAllergyRepository.findAllByRecipeId(recipeId).stream()
                .map(r -> r.getAllergy().getId())
                .toList();
    }

}
