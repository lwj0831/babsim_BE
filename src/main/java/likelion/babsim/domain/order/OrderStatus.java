package likelion.babsim.domain.order;

public enum OrderStatus {
    CONFIRMATION("결제확인"),FORWARDING("출고처리"),DELIVERING("배송중"),ARRIVED("배송완료");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
