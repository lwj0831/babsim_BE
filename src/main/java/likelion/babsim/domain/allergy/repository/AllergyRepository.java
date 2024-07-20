package likelion.babsim.domain.allergy.repository;

import likelion.babsim.domain.allergy.Allergy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRepository extends JpaRepository<Allergy,Long> {
    Allergy findAllergyById(Long allergyId);
}
