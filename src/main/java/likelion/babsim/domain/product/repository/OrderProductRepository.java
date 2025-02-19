package likelion.babsim.domain.product.repository;

import likelion.babsim.domain.product.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>  {
    List<OrderProduct> findByProductId(Long productId);
}
