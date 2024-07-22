package likelion.babsim.domain.review.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import likelion.babsim.web.review.ReviewCreateReqDto;
import likelion.babsim.web.review.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeReviewService {
    private final RecipeReviewRepository recipeReviewRepository;
    private final RecipeRepository recipeRepository;
    private final MemberRepository memberRepository;

    public Double findRatingByRecipeId(Long recipeId){
        return recipeReviewRepository.findAllByRecipeId(recipeId).stream()
                .map(RecipeReview::getRating)
                .mapToDouble(r -> r)
                .average()
                .orElse(0.0);
    }
    public List<ReviewForm> findReviewsByRecipeId(Long recipeId){
        List<RecipeReview> recipeReviews = recipeReviewRepository.findAllByRecipeId(recipeId);
        List<ReviewForm> result = new ArrayList<>();
        for (RecipeReview recipeReview : recipeReviews) {
            ReviewForm reviewForm = ReviewForm.builder()
                    .memberId(recipeReview.getMember().getId())
                    .memberName(recipeReview.getMember().getName())
                    .memberImg(recipeReview.getMember().getMemberImg())
                    .comment(recipeReview.getComment())
                    .rating(recipeReview.getRating())
                    .registerDate(recipeReview.getRegisterDate())
                    .forkedRecipeId(recipeReview.getForkedRecipeId())
                    .build();
            result.add(reviewForm);
        }
        return result;
    }
    @Transactional
    public Long writeReview(Long recipeId, String memberId, ReviewCreateReqDto reviewCreateReqDto){
        RecipeReview recipeReview = RecipeReview.builder()
                .recipe(recipeRepository.findById(recipeId).orElseThrow())
                .member(memberRepository.findById(memberId).orElseThrow())
                .rating(reviewCreateReqDto.getRating())
                .comment(reviewCreateReqDto.getComment())
                .registerDate(LocalDateTime.now())
                .forkedRecipeId(reviewCreateReqDto.getForkedRecipeId())
                .build();
        recipeReviewRepository.save(recipeReview);
        return recipeReview.getId();
    }
}
