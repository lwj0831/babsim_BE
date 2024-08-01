package likelion.babsim.domain.nft.service;

import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.nft.SaleNft;
import likelion.babsim.domain.nft.repository.SaleNftRepository;
import likelion.babsim.web.nft.*;
import likelion.babsim.web.nft.kas.TokenApproveResDto;
import likelion.babsim.web.nft.kas.TokenCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NftService {

    private final NftRepository nftRepository;
    private final KlaytnApiService klaytnApiService;
    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final SaleNftRepository saleNftRepository;

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
        return null; //예외 처리 필요
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
        return null; //예외처리 필요
    }

    @Transactional
    public SaleNftRegisterResDto registerNftSale(Long recipeId, BigDecimal price){
        Nft nft = nftRepository.findByRecipeId(recipeId).orElseThrow();
        SaleNft saleNft = SaleNft.builder()
                .price(price)
                .saleStartTime(LocalDateTime.now())
                .nft(nftRepository.findById(nft.getId()).orElseThrow())
                .build();
        saleNftRepository.save(saleNft);
        return SaleNftRegisterResDto.builder()
                .saleNftId(saleNft.getId())
                .status("register")
                .build();
    }

    @Transactional
    public SaleNftTerminateResDto terminateNftSale(Long recipeId) {
        Nft nft = nftRepository.findByRecipeId(recipeId).orElseThrow();
        SaleNft saleNft = saleNftRepository.findByNft(nft).orElseThrow();
        Long saleNftId = saleNft.getId();

        saleNftRepository.delete(saleNft);
        saleNftRepository.flush();

        return SaleNftTerminateResDto.builder()
                .saleNftId(saleNftId)
                .status("terminate")
                .build();
    }

    public List<SaleNftInfoResDto> findRecommendNfts(){
        List<SaleNft> random10SaleNfts = saleNftRepository.findRandom10SaleNfts();

        List<SaleNftInfoResDto> result = new ArrayList<>();
        for (SaleNft saleNft : random10SaleNfts) {
            Nft nft = nftRepository.findBySaleNft(saleNft).orElseThrow();
            SaleNftInfoResDto saleNftInfoResDto = SaleNftInfoResDto.builder()
                    .nftId(nft.getId())
                    .uri(nft.getUri())
                    .price(saleNftRepository.findByNft(nft).orElseThrow().getPrice())
                    .recipeId(recipeRepository.findByNft(nft).orElseThrow().getId())
                    .recipeName(recipeRepository.findByNft(nft).orElseThrow().getRecipeName())
                    .build();
            result.add(saleNftInfoResDto);
        }
        return result;
    }

    public List<NftInfoResDto> findOwnNft(String memberId){
        List<Nft> nfts = nftRepository.findAllByOwnerId(memberId);
        return nftsToNftInfoResDtoList(nfts);
    }

    public List<NftInfoResDto> nftsToNftInfoResDtoList(List<Nft> nfts){
        return nfts.stream().map(nft -> {
            Optional<SaleNft> findSaleNft = saleNftRepository.findByNft(nft);
            Recipe recipe = recipeRepository.findByNft(nft).orElseThrow();

            return NftInfoResDto.builder()
                    .nftId(nft.getId())
                    .uri(nft.getUri())
                    .price(findSaleNft.map(SaleNft::getPrice).orElse(null))
                    .recipeId(recipe.getId())
                    .recipeName(recipe.getRecipeName())
                    .isSale(findSaleNft.isPresent())
                    .build();
        }).collect(Collectors.toList());
    }
}
