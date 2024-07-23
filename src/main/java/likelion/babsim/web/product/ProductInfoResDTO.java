package likelion.babsim.web.product;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class ProductInfoResDTO {
    private Long id;
    private String name;
    private String img;
    private Long price;
    private Double rate;
}
