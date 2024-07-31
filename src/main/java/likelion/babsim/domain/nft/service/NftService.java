package likelion.babsim.domain.nft.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.sale.repository.SaleRepository;
import likelion.babsim.web.nft.NftApproveResDto;
import likelion.babsim.web.nft.NftCreateResDto;
import likelion.babsim.web.nft.NftInfoResDto;
import likelion.babsim.web.nft.kas.TokenApproveResDto;
import likelion.babsim.web.nft.kas.TokenCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final KlaytnApiService klaytnApiService;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final SaleRepository saleRepository;

    @Transactional
    public NftCreateResDto createNft(Long recipeId, String memberId){ //프론트단에서도 검증 로직 필요
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        String creatorId = recipe.getCreatorId();

        if(memberId.equals(creatorId)){
            String to = memberRepository.findById(creatorId).orElseThrow().getNftAccountAddress();
            String tokenId = "0x"+Long.toHexString(recipeId);
            String uri = recipe.getRecipeName(); //URI로 뭐 줄지 생각해봐야함
            TokenCreateResDto tokenCreateResDto = klaytnApiService.createToken(to, tokenId, uri);
            System.out.println(tokenCreateResDto);
            if(tokenCreateResDto.getStatus().equals("Submitted")) {
                Nft nft = Nft.builder()
                        .tokenId(tokenId)
                        .uri(uri)
                        .ownerId(creatorId)
                        .recipe(recipe)
                        .build();
                nftRepository.save(nft);
                return NftCreateResDto.builder()
                        .id(nft.getId())
                        .tokenId(tokenId)
                        .uri(uri).build();
            }
        }
        return null;
    }

    @Transactional
    public NftApproveResDto approveNft(String memberId, Long nftId){
        Optional<Nft> findNft = nftRepository.findById(nftId);
        if(findNft.isPresent()) {
            Nft nft = findNft.get();
            String ownerAddress = memberRepository.findById(nft.getOwnerId()).orElseThrow().getNftAccountAddress();
            String toAddress = memberRepository.findById(memberId).orElseThrow().getNftAccountAddress();
            String tokenId = nft.getTokenId();
            TokenApproveResDto tokenApproveResDto = klaytnApiService.approveToken(ownerAddress, toAddress, tokenId);
            if(tokenApproveResDto.getStatus().equals("Submitted")){
                nft.setOwnerId(memberId);
                nftRepository.save(nft);
                return NftApproveResDto.builder()
                        .ownerAddress(ownerAddress)
                        .toAddress(toAddress)
                        .tokenId(tokenId)
                        .build();
            }

        }
        return null;
    }

    public List<NftInfoResDto> findRecommendNfts(){
        List<Nft> random10Nfts = nftRepository.findRandom10Nfts();

        List<NftInfoResDto> result = new ArrayList<>();
        for (Nft nft : random10Nfts) {
            NftInfoResDto nftInfoResDto = NftInfoResDto.builder()
                    .nftId(nft.getId())
                    .uri(nft.getUri())
                    .price(saleRepository.findByNft(nft).orElseThrow().getPrice())
                    .recipeId(recipeRepository.findByNft(nft).orElseThrow().getId())
                    .recipeName(recipeRepository.findByNft(nft).orElseThrow().getRecipeName())
                    .build();
            result.add(nftInfoResDto);
        }
        return result;
    }
}
