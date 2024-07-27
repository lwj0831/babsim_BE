package likelion.babsim.domain.likes.service;

import likelion.babsim.domain.likes.Likes;
import likelion.babsim.domain.likes.repository.LikesRepository;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.web.likes.LikesUpdateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;

    public List<Likes> findLikesByMemberId(String memberId){
        return likesRepository.findAllByMemberId(memberId);
    }
    public boolean checkLikesByMemberIdAndRecipeId(String memberId,Long recipeId){
        return likesRepository.existsByMemberIdAndRecipeId(memberId,recipeId);
    }
    
    @Transactional
    public LikesUpdateResDto updateLikesByMemberIdAndRecipeId(String memberId,Long recipeId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        if(!checkLikesByMemberIdAndRecipeId(memberId,recipeId)) {
            Likes likes = Likes.builder()
                    .member(member)
                    .recipe(recipe)
                    .build();
            likesRepository.save(likes);
            return LikesUpdateResDto.builder()
                    .likesId(likes.getId())
                    .memberId(member.getId())
                    .recipeId(recipe.getId())
                    .status("created")
                    .build();
        }
        else {
            Likes likes = likesRepository.findByMemberIdAndRecipeId(memberId, recipeId);
            likesRepository.delete(likes);
            return LikesUpdateResDto.builder()
                    .likesId(likes.getId())
                    .memberId(member.getId())
                    .recipeId(recipe.getId())
                    .status("deleted")
                    .build();
        }
    }
}
