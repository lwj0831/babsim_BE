package likelion.babsim.domain.cookedRecord.controller;

import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.cookedRecord.service.CookedRecordService;
import likelion.babsim.web.cookedRecord.CookedRecordResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cookedRecord")
public class CookedRecordController {
    private CookedRecordService cookedRecordService;

    @PutMapping
    public CookedRecordResDto updateCookedRecord(@RequestParam Long recipeId){
        return cookedRecordService.updateCookedRecord(recipeId);
    }

}
