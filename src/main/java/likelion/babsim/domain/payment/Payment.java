package likelion.babsim.domain.payment;

import jakarta.persistence.*;
import likelion.babsim.domain.order.Orders;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private Long id;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private Long totalAmount;
    private Integer discountAmount;
    private Long deliveryCharge;
    private Long pointEarned;
    private Long pointUsed;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Orders orders;

}
