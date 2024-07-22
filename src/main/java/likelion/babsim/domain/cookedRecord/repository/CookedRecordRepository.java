package likelion.babsim.domain.cookedRecord.repository;

import likelion.babsim.domain.cookedRecord.CookedRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CookedRecordRepository extends JpaRepository<CookedRecord,Long> {
    List<CookedRecord> findTop10ByOrderByCookedCountDesc();
    CookedRecord findByRecipeId(Long recipeId);
}
