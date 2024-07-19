package likelion.babsim.domain.allergy.service;

import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.allergy.repository.MemberAllergyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberAllergyService {
    private final MemberAllergyRepository memberAllergyRepository;

    public List<Long> findAllergiesByMemberId(Long memberId){
        return memberAllergyRepository.findAllByMemberId(memberId).stream()
                .map(m->m.getAllergy().getId())
                .toList();
    }
}
