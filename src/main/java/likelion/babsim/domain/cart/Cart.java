package likelion.babsim.domain.cart;

import jakarta.persistence.*;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.product.CartProduct;
import likelion.babsim.domain.product.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_id")
    private Long id;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CartProduct> cartProducts;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;
}
