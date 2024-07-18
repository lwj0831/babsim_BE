package likelion.babsim.domain.keyword.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.keyword.Keyword;
import likelion.babsim.domain.keyword.repository.KeywordRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KeywordServiceTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private KeywordRepository keywordRepository;

    @Test
    @Transactional
    void findRelatedKeywordsByKeyword() {
        for (int i = 1; i <= 15; i++) {
            Keyword keyword = new Keyword();
            keyword.setKeyword("keyword_" + i);
            keyword.setCount((long) (i*10));
            entityManager.persist(keyword);
        }
        List<Keyword> result = keywordRepository.findByKeywordContaining("keyword");
        assertThat(result).hasSize(15);
        assertThat(result.get(0).getKeyword()).isEqualTo("keyword_1");
        assertThat(result.get(2).getKeyword()).isEqualTo("keyword_3");
    }

    @Test
    @Transactional
    void findPopularKeywords() {
        for (int i = 1; i <= 15; i++) {
            Keyword keyword = new Keyword();
            keyword.setKeyword("keyword_" + i);
            keyword.setCount((long) (i*10));
            entityManager.persist(keyword);
        }
        List<Keyword> topKeywords = keywordRepository.findTop10ByOrderByCountDesc();

        assertThat(topKeywords).hasSize(10);
        assertThat(topKeywords.get(0).getCount()).isEqualTo(150); // 첫 번째로 반환된 엔티티의 count 값 확인
        assertThat(topKeywords.get(9).getCount()).isEqualTo(60);
    }
}