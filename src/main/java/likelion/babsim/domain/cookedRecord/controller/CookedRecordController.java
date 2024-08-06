package likelion.babsim.domain.cookedRecord.controller;

import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.web.cookedRecord.CookedRecordResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cookedRecord")
@CrossOrigin(origins = {"http://localhost:5173"})
public class CookedRecordController {
    private final CookedRecordService cookedRecordService;

    @PutMapping
    public CookedRecordResDto updateCookedRecord(@RequestParam("recipeId") Long recipeId){
        return cookedRecordService.updateCookedRecord(recipeId);
    }

}
