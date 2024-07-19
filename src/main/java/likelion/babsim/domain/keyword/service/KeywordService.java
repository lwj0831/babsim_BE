package likelion.babsim.domain.keyword.service;

import likelion.babsim.domain.keyword.Keyword;
import likelion.babsim.domain.keyword.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class KeywordService {
    private final KeywordRepository keywordRepository;

    public List<String> findRelatedKeywordsByKeyword(String keyword){
        return keywordRepository.findByKeywordContaining(keyword).stream()
                .map(Keyword::getKeyword)
                .toList();
    }
    public List<String> findPopularKeywords(){
        return keywordRepository.findTop10ByOrderByCountDesc().stream()
                .map(Keyword::getKeyword)
                .toList();
    }

}
