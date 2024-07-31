package likelion.babsim.domain.nft.service;

import jakarta.annotation.PostConstruct;
import likelion.babsim.web.nft.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional(readOnly = true)
public class NftService {

    private WebClient webClient;
    @Value("${klaytn.auth.x-chain-id}")
    private String xChainId;
    @Value("${klaytn.auth.authorization}")
    private String authorization;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.create();
    }
    @Transactional
    public AccountCreateResDto createAccount() {
        return webClient.post()
                .uri("wallet-api.klaytnapi.com/v2/account")
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON) // Content-Type을 명시적으로 설정
                .retrieve()
                .bodyToMono(AccountCreateResDto.class)
                .block();
    }

    public AccountReadResDto findAccount() {
        return webClient.get()
                .uri("wallet-api.klaytnapi.com/v2/account")
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AccountReadResDto.class)
                .block();
    }

    public TokenCreateResDto createToken(String to, String id, String uri){
        TokenCreateReqDto tokenCreateReqDto = new TokenCreateReqDto(to,id,uri);

        return webClient.post()
                .uri("https://kip17-api.klaytnapi.com/v2/contract/babsim/token")
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON) // Content-Type을 명시적으로 설정
                .bodyValue(tokenCreateReqDto)//Content-Type: application/json 자동 설정
                .retrieve()
                .bodyToMono(TokenCreateResDto.class)
                .block();

    }
    public TokenReadResDto findTokens(String ownerAddress){
        return webClient.get()
                .uri("https://kip17-api.klaytnapi.com/v2/contract/babsim/owner/"+ownerAddress)
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .retrieve()
                .bodyToMono(TokenReadResDto.class)
                .block();
    }
    public TokenApproveResDto approveToken(String ownerAddress,String toAddress,String tokenId){
        TokenApproveReqDto tokenApproveReqDto = new TokenApproveReqDto(ownerAddress,ownerAddress,toAddress);
        return webClient.post()
                .uri("https://kip17-api.klaytnapi.com/v2/contract/babsim/token/"+tokenId)
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .bodyValue(tokenApproveReqDto)//Content-Type: application/json 자동 설정
                .retrieve()
                .bodyToMono(TokenApproveResDto.class)
                .block();
    }

}

