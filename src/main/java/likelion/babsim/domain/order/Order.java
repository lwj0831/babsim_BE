package likelion.babsim.domain.order;

import jakarta.persistence.*;
import likelion.babsim.domain.record.PaymentRecord;
import likelion.babsim.domain.member.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String recipientName;
    private String recipientPhone;
    private String arrival;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
    @ToString.Exclude
    private PaymentRecord paymentRecord;
}
