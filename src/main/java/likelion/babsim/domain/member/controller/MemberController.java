package likelion.babsim.domain.member.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.babsim.domain.member.service.MemberService;
import likelion.babsim.web.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(origins = {"http://localhost:5173"})
public class MemberController {
    private final MemberService memberService;
    private static final String REST_API_KEY = "f0b7ac898da3a5b19640f297fd76d1be";
    private static final String REDIRECT_URI = "http://localhost:5173/login";
    private static final String CHAR_SET = "UTF-8";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String TOKEN_TYPE = "bearer";
    private static final String CODE_REQ_URL_1 = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=";
    private static final String CODE_REQ_URL_2 = "&redirect_uri=";
    private static final String TOKEN_REQ_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_REQ_URL = "https://kapi.kakao.com/v2/user/me";


    @PostMapping
    public MemberResDTO createMember(@RequestBody MemberReqDTO memberRequestDTO) {
        return memberService.createMember(memberRequestDTO);
    }

    @GetMapping("/{memberId}")
    public MemberResDTO getMember(@PathVariable String memberId) {
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
        String accessToken = getUserAssessToken(restTemplate, kakaoLoginTokenReqDTO.getCode());

        // Get User Info
        Map<String, String> userInfo = getUserInfo(restTemplate, accessToken);

        KakaoLoginResDto kakaoLoginResDto = new KakaoLoginResDto();
        kakaoLoginResDto.setId(UUID.randomUUID().toString());
        kakaoLoginResDto.setName(userInfo.get("name"));
        kakaoLoginResDto.setEmail(userInfo.get("email"));
        kakaoLoginResDto.setImg(userInfo.get("imgUrl"));
        kakaoLoginResDto.setStatus(200);

        return kakaoLoginResDto;
    }

    private String getUserAssessToken(RestTemplate restTemplate, String code) {
        // Header Data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", CHAR_SET);
        // Body Data
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", REST_API_KEY);
        body.add("redirect_uri", REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(TOKEN_REQ_URL, requestEntity, String.class);

        // Get AccessToken
        return extractAccessToken(response.getBody());
    }

    private static String extractAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, String> getUserInfo(RestTemplate restTemplate, String accessToken) {
        // Header Data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", CHAR_SET);
        headers.set("Authorization", "Bearer " + accessToken );

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(USER_INFO_REQ_URL, HttpMethod.GET, requestEntity, String.class);

        String nickname = extractValue(response.getBody(), "kakao_account", "profile", "nickname");
        String profileImageUrl = extractValue(response.getBody(), "kakao_account", "profile", "profile_image_url");
        String email = extractValue(response.getBody(), "kakao_account", "email");

        Map<String, String> info = new HashMap<>();
        info.put("name", nickname);
        info.put("imgUrl", profileImageUrl);
        info.put("email", email);

        return info;
    }

    private static String extractValue(String responseBody, String... keys) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            for (String key : keys) {
                jsonNode = jsonNode.get(key);
            }
            return jsonNode.asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
