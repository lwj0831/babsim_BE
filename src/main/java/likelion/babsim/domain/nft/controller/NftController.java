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
@CrossOrigin(origins = {"http://localhost:5173"})
public class NftController {
    private final NftService nftService;

    @PostMapping
    public NftCreateResDto createNft(@RequestParam Long recipeId, @RequestParam String memberId){
        return nftService.createNft(recipeId,memberId);
    }
    @PostMapping("/{nftId}")
    public NftApproveResDto approveNft(@RequestParam String memberId, @PathVariable Long recipeId){
        return nftService.approveNft(memberId,recipeId);
    }
    @PostMapping("/saleNft")
    public SaleNftRegisterResDto registerSaleNft(@RequestParam Long recipeId, @RequestParam BigDecimal price){
        return nftService.registerNftSale(recipeId,price);
    }
    @DeleteMapping("/saleNft")
    public SaleNftTerminateResDto terminateSaleNft(@RequestParam Long recipeId){
        return nftService.terminateNftSale(recipeId);
    }
    @GetMapping("/saleNft")
    public List<SaleNftInfoResDto> findRecommendSaleNfts(){
        return nftService.findRecommendNfts();
    }
    @GetMapping
    public List<NftInfoResDto> findOwnNfts(@RequestParam String memberId){
        return nftService.findOwnNft(memberId);
    }


}
