package likelion.babsim.domain.sale.repository;

import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.sale.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Long> {
    Optional<Sale> findByNft(Nft nft);
}
