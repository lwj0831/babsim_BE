package likelion.babsim.web.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class ProductDetailResDTO {
    private Long id;
    private String name;
    private String img;
    private Long price;
    private Double rate;

}
