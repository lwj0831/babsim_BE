package likelion.babsim.domain.allergy;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberAllergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_allergy_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="allergy_id")
    private Allergy allergy;
}
