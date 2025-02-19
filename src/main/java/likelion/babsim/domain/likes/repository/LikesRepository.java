package likelion.babsim.domain.likes.repository;

import likelion.babsim.domain.likes.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findAllByMemberId(String memberId);
    boolean existsByMemberIdAndRecipeId(String memberId,Long recipeId);
    Optional<Likes> findByMemberIdAndRecipeId(String memberId, Long recipeId);
}
