package likelion.babsim.domain.review.repository;

import likelion.babsim.domain.review.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    Optional<ProductReview> findByOrderProductId(Long orderProductId);
}
