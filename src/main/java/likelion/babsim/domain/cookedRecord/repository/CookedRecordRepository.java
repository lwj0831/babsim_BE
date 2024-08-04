package likelion.babsim.domain.cookedRecord.repository;

import likelion.babsim.domain.cookedRecord.CookedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CookedRecordRepository extends JpaRepository<CookedRecord,Long> {
    List<CookedRecord> findTop12ByOrderByCookedCountDesc();
    Optional<CookedRecord> findByRecipeId(Long recipeId);
}
