package likelion.babsim.domain.member.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.allergy.Allergy;
import likelion.babsim.domain.allergy.repository.AllergyRepository;
import likelion.babsim.web.member.MemberReqDTO;
import likelion.babsim.web.member.MemberResDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MemberService memberService;

    @Autowired
    private AllergyRepository allergyRepository;

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeReview").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberAllergy ").executeUpdate();
        entityManager.createQuery("DELETE FROM Member ").executeUpdate();

        Allergy allergy1 = Allergy.builder()
                .allergyName("Milk Allergy")
                .build();
        entityManager.persist(allergy1);
        Allergy allergy2 = Allergy.builder()
                .allergyName("Peanut Allergy")
                .build();
        entityManager.persist(allergy2);

        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Transactional
    @DirtiesContext
    void createMemberAndFindByIdTest() {
        Allergy allergy1 = Allergy.builder()
                .allergyName("Milk Allergy")
                .build();

        Allergy allergy2 = Allergy.builder()
                .allergyName("Peanut Allergy")
                .build();
        allergyRepository.save(allergy1);
        allergyRepository.save(allergy2);

        MemberReqDTO memberReqDTO = new MemberReqDTO();
        memberReqDTO.setId("member1");
        memberReqDTO.setName("Member1");
        memberReqDTO.setAge(10);
        memberReqDTO.setEmail("member1@gmail.com");
        memberReqDTO.setJob(0);
        memberReqDTO.setAllergy(List.of(1L));

        MemberResDTO createdMember = memberService.createMember(memberReqDTO);

        assertNotNull(createdMember.getId());
        assertEquals("Member1", createdMember.getName());
        assertEquals("member1@gmail.com", createdMember.getEmail());
        assertEquals(10, createdMember.getAge());
        assertEquals(0, createdMember.getJob().intValue());
        assertFalse(createdMember.getAllergies().isEmpty()); // Check if allergies are set correctly

        String createdMemberId = createdMember.getId();
        MemberResDTO foundMember = memberService.findMemberById(createdMemberId);

        assertEquals(createdMemberId, foundMember.getId());
        assertEquals("Member1", foundMember.getName());
        assertEquals("member1@gmail.com", foundMember.getEmail());
        assertEquals(10, foundMember.getAge());
        assertEquals(0, foundMember.getJob().intValue());
        assertEquals(createdMember.getAllergies(), foundMember.getAllergies());
    }


}
