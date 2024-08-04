package likelion.babsim.domain.nft.service;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import jakarta.annotation.PostConstruct;
import likelion.babsim.web.nft.kas.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Service
@Transactional(readOnly = true)
public class KlaytnApiService {

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
        WebClient webClient = WebClient.builder()
                .baseUrl("https://wallet-api.klaytnapi.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("x-chain-id", xChainId)
                .defaultHeader("Authorization", authorization)
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(10)) // 타임아웃 설정
                                .doOnConnected(conn -> conn
                                        .addHandlerLast(new ReadTimeoutHandler(10)) // Read 타임아웃 설정
                                        .addHandlerLast(new WriteTimeoutHandler(10)) // Write 타임아웃 설정
                                )
                ))
                .build();

        return webClient.post()
                .uri("/v2/account")
                .accept(MediaType.APPLICATION_JSON)
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
    public TokenApproveResDto approveToken(String ownerAddress, String toAddress, String tokenId){
        TokenApproveReqDto tokenApproveReqDto = new TokenApproveReqDto(ownerAddress,ownerAddress,toAddress);
        return webClient.post()
                .uri("https://kip17-api.klaytnapi.com/v2/contract/babsim/token/"+tokenId)
                .header("x-chain-id", xChainId)
                .header("Authorization", authorization)
                .bodyValue(tokenApproveReqDto)
                .retrieve()
                .bodyToMono(TokenApproveResDto.class)
                .block();
    }


}

