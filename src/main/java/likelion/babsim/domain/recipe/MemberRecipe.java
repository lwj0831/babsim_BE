package likelion.babsim.domain.recipe;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MemberRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="member_recipe_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="recipe_id")
    private Recipe recipe;

    @Builder
    public MemberRecipe(Long id, Member member, Recipe recipe) {
        this.id = id;
        this.member = member;
        this.recipe = recipe;
    }
}
