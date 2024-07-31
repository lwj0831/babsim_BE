package likelion.babsim.domain.nft.controller;

import likelion.babsim.domain.nft.service.NftService;
import likelion.babsim.web.nft.NftApproveResDto;
import likelion.babsim.web.nft.NftCreateResDto;
import likelion.babsim.web.nft.SaleNftRegisterResDto;
import likelion.babsim.web.nft.SaleNftTerminateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

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
    public NftApproveResDto approveNft(@RequestParam String memberId, @PathVariable Long nftId){
        return nftService.approveNft(memberId,nftId);
    }
    @PostMapping("/saleNft")
    public SaleNftRegisterResDto registerSaleNft(@RequestParam Long recipeId, @RequestParam BigDecimal price){
        return nftService.registerNftSale(recipeId,price);
    }
    @DeleteMapping("/saleNft")
    public SaleNftTerminateResDto terminateSaleNft(@RequestParam Long recipeId){
        return nftService.terminateNftSale(recipeId);
    }
    
}
