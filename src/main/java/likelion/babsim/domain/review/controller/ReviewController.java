package likelion.babsim.domain.review.controller;

import likelion.babsim.domain.review.service.RecipeReviewService;
import likelion.babsim.web.review.ReviewCreateReqDto;
import likelion.babsim.web.review.ReviewForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@CrossOrigin(origins = {"http://localhost:5173"})
public class ReviewController {
    private final RecipeReviewService recipeReviewService;

    @GetMapping("{recipeId}")
    public List<ReviewForm> getReviewsByRecipeId(@PathVariable Long recipeId){
        return recipeReviewService.findReviewsByRecipeId(recipeId);
    }
    @PostMapping
    public Long writeReview(@RequestParam Long recipeId,@RequestParam String memberId, @RequestBody ReviewCreateReqDto reviewCreateReqDto){
        return recipeReviewService.writeReview(recipeId,memberId,reviewCreateReqDto);
    }
}
