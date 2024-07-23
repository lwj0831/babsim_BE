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
@CrossOrigin(origins = {"http://localhost:5173"})
public class ProductController {
    private final ProductService productService;

    @GetMapping("/recommend")
    public List<ProductInfoResDTO> getMarketProductRecommendation() {
        return productService.getMarketProductRecommendation();
    }

    @GetMapping("/{productId}")
    public ProductDetailResDTO getProductDetailById(@PathVariable Long productId) {
        return productService.getProductDetailById(productId);
    }

}
