package likelion.babsim.web.product;

import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
public class ProductDetailResDTO {
    private Long id;
    private List<String> img;
    private String name;
    private String desc;
    private Long price;
    private Double rate;
    private Integer stock;
}
