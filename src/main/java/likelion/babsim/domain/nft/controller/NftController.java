package likelion.babsim.domain.nft.controller;

import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.service.NftService;
import likelion.babsim.web.nft.NftCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nft")
@CrossOrigin(origins = {"http://localhost:5173"})
public class NftController {
    private final NftService nftService;

    @PostMapping("/create")
    public NftCreateResDto createRecipeNft(@RequestParam Long recipeId){
        return nftService.createNft(recipeId);
    }


}
