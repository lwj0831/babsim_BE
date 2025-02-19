package likelion.babsim.domain.product.service;

import likelion.babsim.domain.product.OrderProduct;
import likelion.babsim.domain.product.Product;
import likelion.babsim.domain.product.repository.OrderProductRepository;
import likelion.babsim.domain.product.repository.ProductRepository;
import likelion.babsim.domain.review.ProductReview;
import likelion.babsim.domain.review.repository.ProductReviewRepository;
import likelion.babsim.web.product.ProductDetailResDTO;
import likelion.babsim.web.product.ProductInfoResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static likelion.babsim.domain.formatter.RecipeImgFormatter.parseImageIdList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductReviewRepository productReviewRepository;


    public List<ProductInfoResDTO> getMarketProductRecommendation() {
        List<Product> products = getRandomProducts(10);
        return getProductInfoResDTOS(products);
    }

    public List<ProductInfoResDTO> getMarketProductHot() {
        List<Product> products = getRandomProducts(10);
        return getProductInfoResDTOS(products);
    }

    private List<ProductInfoResDTO> getProductInfoResDTOS(List<Product> products) {
        List<ProductInfoResDTO> productInfoResDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductInfoResDTO productInfoResDTO = new ProductInfoResDTO();
            productInfoResDTO.setId(product.getId());
            productInfoResDTO.setName(product.getProductName());
            productInfoResDTO.setImg(product.getProductImg());
            productInfoResDTO.setPrice(product.getPrice());
            productInfoResDTO.setRate(getRateById(product.getId()));
            productInfoResDTOList.add(productInfoResDTO);
        }
        return productInfoResDTOList;
    }

    public List<Product> getRandomProducts(int count) {
        return productRepository.findRandomProducts(PageRequest.of(0, count));
    }

    public ProductDetailResDTO getProductDetailById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow();
        ProductDetailResDTO productDetailResDTO = new ProductDetailResDTO();
        productDetailResDTO.setId(product.getId());
        productDetailResDTO.setImg(parseImageIdList(product.getProductDetailImg()));
        productDetailResDTO.setName(product.getProductName());
        productDetailResDTO.setDesc(product.getProductDescription());
        productDetailResDTO.setPrice(product.getPrice());
        productDetailResDTO.setRate(getRateById(product.getId()));
        productDetailResDTO.setStock(product.getStock());

        return productDetailResDTO;
    }

    public Double getRateById(Long productId) {
        double sum = 0.0;
        int cnt = 0;
        List<OrderProduct> orderProducts = orderProductRepository.findByProductId(productId);
        for (OrderProduct orderProduct : orderProducts) {
            Optional<ProductReview> productReview = productReviewRepository.findByOrderProductId(orderProduct.getId());
            if (productReview.isPresent()) {
                sum += productReview.get().getRating();
                cnt++;
            }
        }

        return (sum / cnt);
    }

}
