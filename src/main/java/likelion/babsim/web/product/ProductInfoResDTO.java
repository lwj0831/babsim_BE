package likelion.babsim.web.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfoResDTO {
    private Long id;
    private String name;
    private String img;
    private Long price;
    private Double rate;
}
