package likelion.babsim.domain.keyword.controller;

import likelion.babsim.domain.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/keywords")
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/related")
    public List<String> getRelatedKeywords(@RequestParam String keyword){
        return keywordService.findRelatedKeywordsByKeyword(keyword);
    }

    @GetMapping("/popular")
    public List<String> getPopularKeywords(){
        return keywordService.findPopularKeywords();
    }


}
