package likelion.babsim.web.review;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCreateReqDto {
    private Integer rating;
    private String comment;
    private Long forkRecipeId;

    @Builder
    public ReviewCreateReqDto(Integer rating, String comment, Long forkRecipeId) {
        this.rating = rating;
        this.comment = comment;
        this.forkRecipeId = forkRecipeId;
    }
}
