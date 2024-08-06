package likelion.babsim.domain.keyword.controller;

import likelion.babsim.domain.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/related")
    public List<String> getRelatedKeywords(@RequestParam("keyword") String keyword){
        return keywordService.findRelatedKeywordsByKeyword(keyword);
    }

    @GetMapping("/popular")
    public List<String> getPopularKeywords(){
        return keywordService.findPopularKeywords();
    }


}
