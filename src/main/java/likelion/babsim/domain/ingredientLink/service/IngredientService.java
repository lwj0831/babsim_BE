package likelion.babsim.domain.ingredientLink.service;

import likelion.babsim.domain.ingredientLink.IngredientLink;
import likelion.babsim.domain.ingredientLink.repository.IngredientLinkRepository;
import likelion.babsim.web.recipe.IngredientForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {
    private final IngredientLinkRepository ingredientLinkRepository;

    public String findLinkByIngredientName(String ingredientName){
        Optional<IngredientLink> ingredientForm = ingredientLinkRepository.findByIngredientName(ingredientName);
        return ingredientForm.map(IngredientLink::getLink).orElse(null);
    }
}
