package likelion.babsim.domain.allergy;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="allergy_id")
    private Long id;
    private String allergyName;

    @OneToMany(mappedBy = "allergy",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MemberAllergy> memberAllergies;

    @OneToMany(mappedBy = "allergy",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RecipeAllergy> recipeAllergies;

    @Builder
    public Allergy(Long id, String allergyName) {
        this.id = id;
        this.allergyName = allergyName;
    }
}
