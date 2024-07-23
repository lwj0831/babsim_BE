package likelion.babsim.domain.ingredientLink.repository;

import likelion.babsim.domain.ingredientLink.IngredientLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientLinkRepository extends JpaRepository<IngredientLink,Long> {
    Optional<IngredientLink> findByIngredientName(String ingredient);
}
