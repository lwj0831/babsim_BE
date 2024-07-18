package likelion.babsim.domain.allergy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private String image;

    @OneToMany(mappedBy = "allergy",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MemberAllergy> memberAllergys;

    @OneToMany(mappedBy = "allergy",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<RecipeAllergy> recipeAllergys;
}
