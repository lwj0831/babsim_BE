package likelion.babsim.domain.cookedRecord.service;

import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.repository.CookedRecordRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.recipe.service.RecipeService;
import likelion.babsim.web.cookedRecord.CookedRecordResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CookedRecordService {
    private final CookedRecordRepository cookedRecordRepository;
    private final RecipeRepository recipeRepository; //recipe 의존성 순환 참조 발생

    public List<CookedRecord> findTop10CookedRecords(){
        return cookedRecordRepository.findTop10ByOrderByCookedCountDesc();
    }
    @Transactional
    public CookedRecordResDto updateCookedRecord(Long recipeId){
        Optional<CookedRecord> cookedRecord = cookedRecordRepository.findByRecipeId(recipeId);
        CookedRecord createdCookedRecord;
        if (cookedRecord.isPresent()) {
            createdCookedRecord = cookedRecord.get();
            createdCookedRecord.setCookedCount(createdCookedRecord.getCookedCount()+1);
        } else {
            createdCookedRecord= CookedRecord.builder()
                    .cookedCount(0L)
                    .recipe(recipeRepository.findById(recipeId).orElseThrow()).build();
        }
        cookedRecordRepository.save(createdCookedRecord);
        return CookedRecordResDto.builder()
                .id(createdCookedRecord.getId())
                .cookedCount(createdCookedRecord.getCookedCount())
                .build();
    }
}
