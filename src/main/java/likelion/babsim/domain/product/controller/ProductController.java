package likelion.babsim.domain.product.controller;

import likelion.babsim.domain.product.service.ProductService;
import likelion.babsim.web.product.ProductDetailResDTO;
import likelion.babsim.web.product.ProductInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class ProductController {
    private final ProductService productService;

    @GetMapping("/recommend")
    public List<ProductInfoResDTO> getMarketProductRecommendation() {
        return productService.getMarketProductRecommendation();
    }

    @GetMapping("/{productId}")
    public ProductDetailResDTO getProductDetailById(@PathVariable("productId") Long productId) {
        return productService.getProductDetailById(productId);
    }

    @GetMapping("/hot")
    public List<ProductInfoResDTO> getHotProducts() {
        return productService.getMarketProductHot();
    }

}
