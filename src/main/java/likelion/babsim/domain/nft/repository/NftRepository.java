package likelion.babsim.domain.nft.repository;

import likelion.babsim.domain.nft.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NftRepository extends JpaRepository<Nft,Long> {
    
}
