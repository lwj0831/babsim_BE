package likelion.babsim.domain.nft.controller;

import likelion.babsim.domain.nft.service.NftService;
import likelion.babsim.web.nft.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nft")
@CrossOrigin(origins = {"https://babsim-59d06.web.app"})
public class NftController {
    private final NftService nftService;

    @PostMapping("/create")
    public NftCreateResDto createNft(@RequestParam("recipeId") Long recipeId, @RequestParam("memberId") String memberId){
        return nftService.createNft(recipeId,memberId);
    }
    @PostMapping("/approve")
    public NftApproveResDto approveNft(@RequestParam("memberId") String memberId, @RequestParam("recipeId") Long recipeId){
        return nftService.approveNft(memberId,recipeId);
    }
    @PostMapping("/saleNft")
    public SaleNftRegisterResDto registerSaleNft(@RequestParam("recipeId") Long recipeId, @RequestParam("price") BigDecimal price){
        return nftService.registerNftSale(recipeId,price);
    }
    @DeleteMapping("/saleNft")
    public SaleNftTerminateResDto terminateSaleNft(@RequestParam("recipeId") Long recipeId){
        return nftService.terminateNftSale(recipeId);
    }
    @GetMapping("/saleNft")
    public List<SaleNftInfoResDto> findRecommendSaleNfts(){
        return nftService.findRecommendNfts();
    }
    @GetMapping
    public List<NftInfoResDto> findOwnNfts(@RequestParam("memberId") String memberId){
        return nftService.findOwnNft(memberId);
    }

    @GetMapping("/transactionBeforeInfo")
    public NftTransactionBeforeDto findNftTransactionBeforeInfo(@RequestParam("recipeId") Long recipeId, @RequestParam("memberId") String memberId){
        return nftService.findNftTransactionBeforeInfo(recipeId,memberId);
    }

}
