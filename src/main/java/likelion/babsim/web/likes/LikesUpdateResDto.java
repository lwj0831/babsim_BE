package likelion.babsim.web.likes;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LikesUpdateResDto {
    Long likesId;
    String memberId;
    Long recipeId;
    String status;

    @Builder
    public LikesUpdateResDto(Long likesId, String memberId, Long recipeId,String status) {
        this.likesId = likesId;
        this.memberId = memberId;
        this.recipeId = recipeId;
        this.status = status;
    }
}
