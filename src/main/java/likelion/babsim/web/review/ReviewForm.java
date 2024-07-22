package likelion.babsim.web.review;

import lombok.Builder;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
public class ReviewForm {
    private String memberId;
    private String memberName;
    private String memberImg;
    private Integer rating;
    private String comment;
    private LocalDateTime registerDate;
    private Long forkedRecipeId;

    @Builder
    public ReviewForm(String memberId, String memberName, String memberImg, Integer rating, String comment, LocalDateTime registerDate, Long forkedRecipeId) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberImg = memberImg;
        this.rating = rating;
        this.comment = comment;
        this.registerDate = registerDate;
        this.forkedRecipeId = forkedRecipeId;
    }
}
