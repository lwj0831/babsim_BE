package likelion.babsim.domain.member.controller;

import likelion.babsim.domain.member.service.MemberService;
import likelion.babsim.web.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(origins = {"http://localhost:5173"})
public class MemberController {
    private final MemberService memberService;
    private static final String REST_API_KEY = "f0b7ac898da3a5b19640f297fd76d1be";
    private static final String REDIRECT_URI = "http://localhost:5173/login";
    private static final String CODE_REQ_URL_1 = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=";
    private static final String CODE_REQ_URL_2 = "&redirect_uri=";


    @PostMapping
    public MemberResDTO createMember(@RequestBody MemberReqDTO memberRequestDTO) {
        return memberService.createMember(memberRequestDTO);
    }

    @GetMapping("/{memberId}")
    public MemberResDTO getMember(@PathVariable("memberId") String memberId) {
        return memberService.findMemberById(memberId);
    }

    @GetMapping("/kakao/redirect")
    public KakaoLoginRedirectResDTO kakaoRedirect() {
        return new KakaoLoginRedirectResDTO(CODE_REQ_URL_1 + REST_API_KEY+ CODE_REQ_URL_2 + REDIRECT_URI);
    }

    @PostMapping("/kakao")
    public KakaoLoginResDto kakaoLogin(@RequestBody KakaoLoginTokenReqDTO kakaoLoginTokenReqDTO) {
        RestTemplate restTemplate = new RestTemplate();

        // Get AccessToken
        String accessToken = memberService.getUserAssessToken(restTemplate, kakaoLoginTokenReqDTO.getCode());
        // Get User Info
        Map<String, String> userInfo = memberService.getUserInfo(restTemplate, accessToken);

        KakaoLoginResDto kakaoLoginResDto = new KakaoLoginResDto();
        kakaoLoginResDto.setId(userInfo.get("id"));
        kakaoLoginResDto.setName(userInfo.get("name"));
        kakaoLoginResDto.setEmail(userInfo.get("email"));
        kakaoLoginResDto.setImg(userInfo.get("imgUrl"));

        return kakaoLoginResDto;
    }


}
