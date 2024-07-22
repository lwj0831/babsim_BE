package likelion.babsim.domain.cookedRecord.service;

import jakarta.persistence.EntityManager;
import likelion.babsim.domain.cookedRecord.CookedRecord;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.web.cookedRecord.CookedRecordResDto;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class CookedRecordServiceTest {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CookedRecordService cookedRecordService;

    @BeforeEach
    void setUp() {
        entityManager.createQuery("DELETE FROM Recipe").executeUpdate();
        entityManager.createQuery("DELETE FROM Tag").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeReview").executeUpdate();
        entityManager.createQuery("DELETE FROM RecipeAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM Member").executeUpdate();
        entityManager.createQuery("DELETE FROM Likes").executeUpdate();
        entityManager.createQuery("DELETE FROM MemberAllergy").executeUpdate();
        entityManager.createQuery("DELETE FROM CookedRecord").executeUpdate();

        Recipe recipe = Recipe.builder()
                .id(1L)
                .build();
        entityManager.merge(recipe);
        Recipe recipe2 = Recipe.builder()
                .id(2L)
                .build();
        entityManager.merge(recipe2);

        CookedRecord cookedRecord1 = CookedRecord.builder()
                .cookedCount(0L)
                .recipe(recipe)
                .build();
        entityManager.merge(cookedRecord1);
        CookedRecord cookedRecord2 = CookedRecord.builder()
                .cookedCount(5L)
                .recipe(recipe2)
                .build();
        entityManager.merge(cookedRecord2);
    }
    @Test
    @Transactional
    void updateCookedRecord() {
        CookedRecordResDto cookedRecordResDto = cookedRecordService.updateCookedRecord(1L);
        Assertions.assertThat(cookedRecordResDto.getCookedCount()).isEqualTo(1L);
        CookedRecordResDto cookedRecordResDto2 = cookedRecordService.updateCookedRecord(2L);
        Assertions.assertThat(cookedRecordResDto2.getCookedCount()).isEqualTo(6L);
    }
}