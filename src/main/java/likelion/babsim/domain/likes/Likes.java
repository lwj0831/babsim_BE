package likelion.babsim.domain.likes;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="recipe_id")
    private Recipe recipe;
}
