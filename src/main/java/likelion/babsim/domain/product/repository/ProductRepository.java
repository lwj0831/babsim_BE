package likelion.babsim.domain.product.repository;

import likelion.babsim.domain.product.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT i FROM Product i ORDER BY FUNCTION('RAND') ASC")
    List<Product> findRandomProducts(Pageable pageable);
}
