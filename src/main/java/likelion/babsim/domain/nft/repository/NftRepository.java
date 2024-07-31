package likelion.babsim.domain.nft.repository;

import likelion.babsim.domain.nft.Nft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NftRepository extends JpaRepository<Nft,Long> {
    List<Nft> findAllByOwnerId(String ownerId);
}
