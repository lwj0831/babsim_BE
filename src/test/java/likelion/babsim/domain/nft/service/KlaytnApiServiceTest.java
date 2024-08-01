package likelion.babsim.domain.nft.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class KlaytnApiServiceTest {

    @Autowired
    private KlaytnApiService klaytnApiService;

    @Test
    void createAccount() {
        log.info("{}", klaytnApiService.createAccount());
    }

    @Test
    void findAccount() {
        log.info("{}", klaytnApiService.findAccount());
    }

    @Test
    void createToken() {
        log.info("{}", klaytnApiService.createToken("0x4cF3e87CEec82E4d5E00862A39ECf690574b9d69","0x3","https://recipe1.ezmember.co.kr/cache/recipe/2015/05/14/aeb07b5d6070aabe0f3fcf5f4c3b54de1.jpg"));
    }

    @Test
    void findTokens() {
        log.info("{}", klaytnApiService.findTokens("0xf7FA87fa1ea77d088C70964e270ACB6F9d40DD1f"));
    }

    @Test
    void approveToken() {
        log.info("{}", klaytnApiService.approveToken("0x4cF3e87CEec82E4d5E00862A39ECf690574b9d69","0xf7FA87fa1ea77d088C70964e270ACB6F9d40DD1f","0x2"));
    }
}