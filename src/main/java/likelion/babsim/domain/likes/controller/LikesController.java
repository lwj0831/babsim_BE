package likelion.babsim.domain.likes.controller;

import likelion.babsim.domain.likes.service.LikesService;
import likelion.babsim.web.likes.LikesUpdateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/likes")
@CrossOrigin(origins = {"http://localhost:5173"})
public class LikesController {
    private final LikesService likesService;

    @PostMapping
    public LikesUpdateResDto updateLikes(String memberId,Long recipeId){
        return likesService.updateLikesByMemberIdAndRecipeId(memberId,recipeId);
    }

}
