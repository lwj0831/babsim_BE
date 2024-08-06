package likelion.babsim.domain.review.controller;

import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.web.review.ReviewCreateReqDto;
import likelion.babsim.web.review.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class ReviewController {
    private final RecipeReviewService recipeReviewService;

    @GetMapping("/{recipeId}")
    public List<ReviewForm> getReviewsByRecipeId(@PathVariable("recipeId") Long recipeId){
        return recipeReviewService.findReviewsByRecipeId(recipeId);
    }
    @PostMapping
    public Long writeReview(@RequestParam("recipeId") Long recipeId,@RequestParam("memberId") String memberId, @RequestBody ReviewCreateReqDto reviewCreateReqDto){
        return recipeReviewService.writeReview(recipeId,memberId,reviewCreateReqDto);
    }
}
