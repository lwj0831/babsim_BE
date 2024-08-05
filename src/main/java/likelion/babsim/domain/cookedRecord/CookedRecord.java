package likelion.babsim.domain.cookedRecord;

import jakarta.persistence.*;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CookedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cooked_record_id")
    private Long id;

    private Long cookedCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Builder
    public CookedRecord(Long cookedCount, Recipe recipe) {
        this.cookedCount = cookedCount;
        this.recipe = recipe;
    }

    public void setCookedCount(Long cookedCount) {
        this.cookedCount = cookedCount;
    }
}
