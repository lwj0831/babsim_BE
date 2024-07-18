package likelion.babsim.domain.product;

import jakarta.persistence.*;
import likelion.babsim.domain.order.Orders;
import likelion.babsim.domain.review.ProductReview;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_product_id")
    private Long id;

    private Long orderAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="product_id")
    private Product product;

    @OneToMany(mappedBy = "orderProduct")
    @ToString.Exclude
    private List<ProductReview> productReviews;
}
