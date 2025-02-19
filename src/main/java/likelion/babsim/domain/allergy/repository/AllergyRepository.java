package likelion.babsim.domain.allergy.repository;

import likelion.babsim.domain.allergy.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AllergyRepository extends JpaRepository<Allergy,Long> {
    Optional<Allergy> findAllergyById(Long allergyId);
    Optional<Allergy> findByAllergyName(String allergyName);
}
