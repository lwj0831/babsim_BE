package likelion.babsim.domain.keyword.repository;

import likelion.babsim.domain.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> findByKeywordContaining(String keyword);
    List<Keyword> findTop10ByOrderByCountDesc();
}
