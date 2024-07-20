package likelion.babsim.domain.allergy.service;

import likelion.babsim.domain.allergy.repository.MemberAllergyRepository;
import likelion.babsim.domain.allergy.repository.RecipeAllergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllergyService {
    private final MemberAllergyRepository memberAllergyRepository;
    private final RecipeAllergyRepository recipeAllergyRepository;
    public List<Long> findAllergiesByMemberId(String memberId){
        return memberAllergyRepository.findAllByMemberId(memberId).stream()
                .map(m->m.getAllergy().getId())
                .toList();
    }

    public List<Long> findAllergiesByRecipeId(Long recipeId){
        return recipeAllergyRepository.findAllByRecipeId(recipeId).stream()
                .map(r -> r.getAllergy().getId())
                .toList();
    }
}
