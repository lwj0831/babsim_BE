package likelion.babsim.domain.review;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.product.OrderProduct;
import likelion.babsim.domain.recipe.Recipe;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_review_id")
    private Long id;

    private String title;
    private int rating;
    private String comment;
    private LocalDateTime registerDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProduct orderProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
