package likelion.babsim.domain.product;

import jakarta.persistence.*;
import likelion.babsim.domain.cart.Cart;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")
    private Long id;

    private String productImg;
    private String productName;
    private Long price;
    private String productDescription;
    private Integer stock;
    private Integer discountRate;
    private String productDetailImg;
    private LocalDateTime registerDate;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<CartProduct> cartProducts;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List<OrderProduct> orderProducts;


}
