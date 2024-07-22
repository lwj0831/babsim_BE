package likelion.babsim.web.review;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewCreateReqDto {
    private Integer rating;
    private String comment;
    private Long forkedRecipeId;

    @Builder
    public ReviewCreateReqDto(Integer rating, String comment, Long forkedRecipeId) {
        this.rating = rating;
        this.comment = comment;
        this.forkedRecipeId = forkedRecipeId;
    }
}
