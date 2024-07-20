package likelion.babsim.domain.allergy.repository;

import likelion.babsim.domain.allergy.MemberAllergy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberAllergyRepository extends JpaRepository<MemberAllergy,Long> {
    List<MemberAllergy> findAllByMemberId(String memberId);
}
