package likelion.babsim.domain.cookedRecord.service;

import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.repository.CookedRecordRepository;
import likelion.babsim.domain.recipe.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CookedRecordService {
    private final CookedRecordRepository cookedRecordRepository;

    public List<Recipe> findTop10Recipes(){
        return cookedRecordRepository.findTop10ByOrderByCookedCountDesc().stream()
                .map(CookedRecord::getRecipe)
                .toList();
    }
}
