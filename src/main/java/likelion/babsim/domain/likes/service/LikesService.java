package likelion.babsim.domain.likes.service;

import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    public List<Likes> findLikesByMemberId(String memberId){
        return likesRepository.findAllByMemberId(memberId);
    }
    public boolean checkLikesByMemberIdAndRecipeId(String memberId,Long recipeId){
        return likesRepository.existsByMemberIdAndRecipeId(memberId,recipeId);
    }
}
