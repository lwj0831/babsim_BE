package likelion.babsim.domain.likes.controller;

import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.web.likes.LikesUpdateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@CrossOrigin(origins = {"http://localhost:5173"})
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    @Transactional(readOnly = false)
    public LikesUpdateResDto updateLikes(@RequestParam("recipeId") Long recipeId, @RequestParam("memberId") String memberId){
        return likesService.updateLikesByMemberIdAndRecipeId(memberId,recipeId);
    }

}
