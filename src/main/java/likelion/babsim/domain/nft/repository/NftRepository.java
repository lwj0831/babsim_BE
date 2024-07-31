package likelion.babsim.domain.nft.repository;

import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.recipe.Recipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NftRepository extends JpaRepository<Nft,Long> {
    List<Nft> findAllByOwnerId(String ownerId);

    default List<Nft> findRandom10Nfts() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Nft> page = findAll(pageable);
        return page.getContent();
    }
}
