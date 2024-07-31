package likelion.babsim.domain.nft.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.web.nft.NftCreateResDto;
import likelion.babsim.web.nft.kas.TokenCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final KlaytnApiService klaytnApiService;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;

    @Transactional
    public NftCreateResDto createNft(Long recipeId){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        String creatorId = recipe.getCreatorId();

        String to = memberRepository.findById(creatorId).orElseThrow().getNftAccountAddress();
        String tokenId = "0x"+recipeId;
        String uri = recipe.getRecipeName(); //생각해봐야함
        TokenCreateResDto tokenCreateResDto = klaytnApiService.createToken(to, tokenId, uri);
        System.out.println(tokenCreateResDto);
        if(tokenCreateResDto.getStatus().equals("Submitted")) {
            Nft nft = Nft.builder()
                    .tokenId(tokenId)
                    .uri(uri)
                    .recipe(recipe)
                    .build();
            nftRepository.save(nft);
            return NftCreateResDto.builder()
                    .id(nft.getId())
                    .tokenId(tokenId)
                    .uri(uri).build();
        }
        return null;
    }

    public boolean checkCreatedNftByRecipeId(Long recipeId){
        return nftRepository.existsByRecipeId(recipeId);
    }

}
