package likelion.babsim.domain.member.service;

import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.allergy.repository.AllergyRepository;
import likelion.babsim.domain.allergy.repository.MemberAllergyRepository;
import likelion.babsim.domain.member.Job;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.web.member.MemberReqDTO;
import likelion.babsim.web.member.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAllergyRepository memberAllergyRepository;
    private final AllergyRepository allergyRepository;

    public MemberResDTO createMember(MemberReqDTO memberRequestDTO) {
        String name = memberRequestDTO.getName();
        Integer age = memberRequestDTO.getAge();
        String email = memberRequestDTO.getEmail();
        Job job = Job.values()[memberRequestDTO.getJob()];
        LocalDateTime registerDate = LocalDateTime.now();

        Member member = Member.dtoBuilder()
                .name(name)
                .age(age)
                .email(email)
                .job(job)
                .registerDate(registerDate)
                .build();

        memberRepository.save(member);

        List<Long> allergyIds = memberRequestDTO.getAllergy();
        List<MemberAllergy> memberAllergies = new ArrayList<>();
        for (Long allergyId : allergyIds) {
            // Create a new MemberAllergy instance
            MemberAllergy memberAllergy = MemberAllergy.builder()
                    .member(member)
                    .allergy(allergyRepository.findAllergyById(allergyId))
                    .build();
            memberAllergies.add(memberAllergy);
            memberAllergyRepository.save(memberAllergy);
        }
        member.setMemberAllergies(memberAllergies);

        return MemberResDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .age(member.getAge())
                .job(member.getJob().ordinal())
                .allergies(allergyIds)
                .build();
    }

    public MemberResDTO findMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<MemberAllergy> allergies = memberAllergyRepository.findAllByMemberId(memberId);
        List<Long> allergyIds = new ArrayList<>();
        for (MemberAllergy memberAllergy : allergies) {
            allergyIds.add(memberAllergy.getAllergy().getId());
        }
        return MemberResDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .img("")  // TODO get member img from firebase
                .email(member.getEmail())
                .age(member.getAge())
                .job(member.getJob().ordinal())
                .allergies(allergyIds)
                .build();
    }


}
