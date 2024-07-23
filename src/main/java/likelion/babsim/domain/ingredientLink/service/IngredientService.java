package likelion.babsim.domain.ingredientLink.service;

import likelion.babsim.domain.ingredientLink.repository.IngredientLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {
    private final IngredientLinkRepository ingredientLinkRepository;

    public String findLinkByIngredientName(String ingredientName){
        return ingredientLinkRepository.findByIngredientName(ingredientName).orElseThrow().getLink();
    }
}
