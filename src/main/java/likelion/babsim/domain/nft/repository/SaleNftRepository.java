package likelion.babsim.domain.nft.repository;

import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.SaleNft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleNftRepository extends JpaRepository<SaleNft,Long> {
    Optional<SaleNft> findByNft(Nft nft);
    default List<SaleNft> findRandom10SaleNfts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SaleNft> page = findAll(pageable);
        return page.getContent();
    }
    void deleteByNftId(Long nftId);
}
