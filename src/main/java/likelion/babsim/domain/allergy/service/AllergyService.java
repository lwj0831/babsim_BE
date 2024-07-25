package likelion.babsim.domain.allergy.service;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        allergyRepository.save(new Allergy(1L, "알류"));
        allergyRepository.save(new Allergy(2L, "우유"));
        allergyRepository.save(new Allergy(3L, "메밀"));
        allergyRepository.save(new Allergy(4L, "땅콩"));
        allergyRepository.save(new Allergy(5L, "대두"));
        allergyRepository.save(new Allergy(6L, "밀"));
        allergyRepository.save(new Allergy(7L, "잣"));
        allergyRepository.save(new Allergy(8L, "호두"));
        allergyRepository.save(new Allergy(9L, "게"));
        allergyRepository.save(new Allergy(10L, "새우"));
        allergyRepository.save(new Allergy(11L, "오징어"));
        allergyRepository.save(new Allergy(12L, "고등어"));
        allergyRepository.save(new Allergy(13L, "조개류"));
        allergyRepository.save(new Allergy(14L, "복숭아"));
        allergyRepository.save(new Allergy(15L, "토마토"));
        allergyRepository.save(new Allergy(16L, "닭고기"));
        allergyRepository.save(new Allergy(17L, "돼지고기"));
        allergyRepository.save(new Allergy(18L, "쇠고기"));
        allergyRepository.save(new Allergy(19L, "아황산류"));
    }

}
