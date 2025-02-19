package likelion.babsim.domain.nft.service;

import jakarta.persistence.EntityNotFoundException;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.nft.Nft;
import likelion.babsim.domain.nft.repository.NftRepository;
import likelion.babsim.domain.point.service.PointService;
import likelion.babsim.domain.recipe.Recipe;
import likelion.babsim.domain.recipe.repository.RecipeRepository;
import likelion.babsim.domain.nft.SaleNft;
import likelion.babsim.domain.nft.repository.SaleNftRepository;
import likelion.babsim.exception.CreateNftException;
import likelion.babsim.web.nft.*;
import likelion.babsim.web.nft.kas.TokenApproveResDto;
import likelion.babsim.web.nft.kas.TokenCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
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
    private final PointService pointService;

    @Transactional
    public NftCreateResDto createNft(Long recipeId, String memberId){ //프론트단에서도 검증 로직 필요
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow();
        String creatorId = recipe.getCreatorId();

        if(memberId.equals(creatorId)){
            String to = memberRepository.findById(creatorId).orElseThrow().getNftAccountAddress();
            String tokenId = "0x"+Long.toHexString(recipeId);
            /*String tokenId = "0x"+recipeId;*/
            String uri = extractFirstId(recipe.getRecipeImgs()); //URI로 recipeImgs의 첫번째 id제공
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
        throw new CreateNftException("member is not owner of recipe!");
    }
    private static String extractFirstId(String idList) {
        String[] ids = idList.split(",");
        if (ids.length > 0) {
            return ids[0];
        } else {
            return "";
        }
    }

    @Transactional
    public NftApproveResDto approveNft(String memberId, Long recipeId){
        Nft nft = nftRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Nft not found with recipeId " + recipeId));

        Member owner = memberRepository.findById(nft.getOwnerId()).orElseThrow();
        Member to = memberRepository.findById(memberId).orElseThrow();
        String ownerAddress = owner.getNftAccountAddress();
        String toAddress = to.getNftAccountAddress();
        String tokenId = nft.getTokenId();

        TokenApproveResDto tokenApproveResDto = klaytnApiService.approveToken(ownerAddress, toAddress, tokenId);
        if(tokenApproveResDto.getStatus().equals("Submitted")) {
            nft.setOwnerId(memberId);
            nftRepository.save(nft);

            pointService.makePointTransactions(memberId, nft.getOwnerId(), "토큰 거래", saleNftRepository.findByNft(nft)
                    .orElseThrow(() -> new EntityNotFoundException("SaleNft not found with Nft " + nft)).getPrice());
            terminateNftSale(recipeRepository.findByNft(nft)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe not found with Nft " + nft)).getId());//saleNft 제거(판매등록해제)
            return NftApproveResDto.builder()
                    .ownerName(owner.getName())
                    .toName(to.getName())
                    .tokenId(tokenId)
                    .build();
        }
        throw new EmptyResultDataAccessException(1);
    }

    @Transactional
    public SaleNftRegisterResDto registerNftSale(Long recipeId, BigDecimal price){
        Nft nft = nftRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Nft not found with recipeId " + recipeId));
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
        Nft nft = nftRepository.findByRecipeId(recipeId)
                .orElseThrow(() -> new EntityNotFoundException("Nft not found with recipeId " + recipeId));
        SaleNft saleNft = saleNftRepository.findByNft(nft).orElseThrow();
        Long saleNftId = saleNft.getId();

        if (nft != null) {
            nft.setSaleNft(null);  // 관계 제거
        }
        saleNftRepository.delete(saleNft);

        return SaleNftTerminateResDto.builder()
                .saleNftId(saleNftId)
                .status("terminate")
                .build();
    }

    public List<SaleNftInfoResDto> findRecommendNfts(){
        List<SaleNft> random10SaleNfts = saleNftRepository.findRandom10SaleNfts();

        List<SaleNftInfoResDto> result = new ArrayList<>();
        for (SaleNft saleNft : random10SaleNfts) {
            Nft nft = nftRepository.findBySaleNft(saleNft)
                    .orElseThrow(() -> new EntityNotFoundException("Nft not found with saleNft " + saleNft));
            SaleNftInfoResDto saleNftInfoResDto = SaleNftInfoResDto.builder()
                    .nftId(nft.getId())
                    .uri(nft.getUri())
                    .price(saleNftRepository.findByNft(nft).orElseThrow().getPrice())
                    .recipeId(recipeRepository.findByNft(nft)
                            .orElseThrow(() -> new EntityNotFoundException("Recipe not found with Nft " + nft)).getId())
                    .recipeName(recipeRepository.findByNft(nft)
                            .orElseThrow(() -> new EntityNotFoundException("Recipe not found with Nft " + nft)).getRecipeName())
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
            Recipe recipe = recipeRepository.findByNft(nft).orElseThrow(() -> new EntityNotFoundException("Recipe not found with Nft " + nft));

            return NftInfoResDto.builder()
                    .nftId(nft.getId())
                    .uri(nft.getUri())
                    .price(findSaleNft.map(SaleNft::getPrice).orElse(null))
                    .recipeId(recipe.getId())
                    .recipeName(recipe.getRecipeName())
                    .nftSaleStatus(findSaleNft.isPresent())
                    .build();
        }).collect(Collectors.toList());
    }

    public NftTransactionBeforeDto findNftTransactionBeforeInfo(Long recipeId,String memberId){
        BigDecimal nftPrice = nftRepository.findByRecipeId(recipeId)
                .orElseThrow(()->new EntityNotFoundException("nft is not found by recipeId"+recipeId)).getSaleNft().getPrice();
        BigDecimal point = pointService.getPointByMemberId(memberId);
        return NftTransactionBeforeDto.builder()
                .nftPrice(nftPrice)
                .point(point)
                .available(nftPrice.compareTo(point)<=0).build();

    }
}
