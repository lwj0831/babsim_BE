package likelion.babsim.domain.allergy.service;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.repository.AllergyRepository;
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
    private final AllergyRepository allergyRepository;

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
    public Allergy findAllergyByAllergyName(String allergyName){
        return allergyRepository.findByAllergyName(allergyName)
                .orElseThrow(() -> new EntityNotFoundException("Allergy not found with name " + allergyName));
    }

}
