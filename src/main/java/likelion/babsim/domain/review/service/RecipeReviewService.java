package likelion.babsim.domain.review.service;

import likelion.babsim.domain.review.RecipeReview;
import likelion.babsim.domain.review.repository.RecipeReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecipeReviewService {
    private final RecipeReviewRepository recipeReviewRepository;

    public Double findRatingByRecipeId(Long recipeId){
        return recipeReviewRepository.findAllByRecipeId(recipeId).stream()
                .map(RecipeReview::getRating)
                .mapToDouble(r -> r)
                .average()
                .orElse(0.0);
    }
}
