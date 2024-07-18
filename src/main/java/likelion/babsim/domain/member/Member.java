package likelion.babsim.domain.member;

import jakarta.persistence.*;
import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.cart.Cart;
import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.order.Order;
import likelion.babsim.domain.review.ProductReview;
import likelion.babsim.domain.review.RecipeReview;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    private String name;
    private Integer age;
    private String email;
    private Job job;
    private String address;
    private Membership membership;
    private Long point;
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<RecipeReview> recipeReviews;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Likes> likes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<MemberAllergy> memberAllergies;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Order> orders;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<ProductReview> productReviews;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Cart cart;
}
