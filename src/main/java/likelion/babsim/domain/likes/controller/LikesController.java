package likelion.babsim.domain.likes.controller;

import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.web.likes.LikesUpdateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@CrossOrigin(origins = {"http://localhost:5173"})
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    public LikesUpdateResDto updateLikes(@RequestParam("recipeId") Long recipeId, @RequestParam("memberId") String memberId){
        return likesService.updateLikesByMemberIdAndRecipeId(memberId,recipeId);
    }

}
