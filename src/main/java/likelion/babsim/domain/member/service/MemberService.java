package likelion.babsim.domain.member.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import likelion.babsim.domain.allergy.MemberAllergy;
import likelion.babsim.domain.allergy.repository.AllergyRepository;
import likelion.babsim.domain.allergy.repository.MemberAllergyRepository;
import likelion.babsim.domain.member.Job;
import likelion.babsim.domain.member.Member;
import likelion.babsim.domain.member.repository.MemberRepository;
import likelion.babsim.domain.point.Point;
import likelion.babsim.domain.point.PointType;
import likelion.babsim.domain.point.repository.PointRepository;
import likelion.babsim.domain.point.service.PointService;
import likelion.babsim.domain.nft.service.KlaytnApiService;
import likelion.babsim.web.member.MemberReqDTO;
import likelion.babsim.web.member.MemberResDTO;
import likelion.babsim.web.nft.kas.AccountCreateResDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberAllergyRepository memberAllergyRepository;
    private final AllergyRepository allergyRepository;
    private final PointRepository pointRepository;
    private final PointService pointService;
    private final KlaytnApiService klaytnApiService;

    private static final String REST_API_KEY = "f0b7ac898da3a5b19640f297fd76d1be";
    private static final String REDIRECT_URI = "http://localhost:5173/login";
    private static final String CHAR_SET = "UTF-8";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String TOKEN_TYPE = "bearer ";
    private static final String TOKEN_REQ_URL = "https://kauth.kakao.com/oauth/token";
    private static final String USER_INFO_REQ_URL = "https://kapi.kakao.com/v2/user/me";

    @Transactional(readOnly = false)
    public MemberResDTO createMember(MemberReqDTO memberRequestDTO) {
        String id = memberRequestDTO.getId();
        String name = memberRequestDTO.getName();
        String memberImg = memberRequestDTO.getImg();
        String email = memberRequestDTO.getEmail();
        Job job = Job.values()[memberRequestDTO.getJob()];
        LocalDateTime registerDate = LocalDateTime.now();
        AccountCreateResDto response = klaytnApiService.createAccount();
        String address = response.getAddress();

        Member member = Member.dtoBuilder()
                .id(id)
                .name(name)
                .memberImg(memberImg)
                .email(email)
                .job(job)
                .registerDate(registerDate)
                .nftAccountAddress(address)
                .build();

        memberRepository.save(member);

        List<Long> allergyIds = memberRequestDTO.getAllergy();
        List<MemberAllergy> memberAllergies = new ArrayList<>();
        for (Long allergyId : allergyIds) {
            // Create a new MemberAllergy instance
            MemberAllergy memberAllergy = MemberAllergy.builder()
                    .member(member)
                    .allergy(allergyRepository.findAllergyById(allergyId))
                    .build();
            System.out.println(memberAllergy.getMember().getId());
            System.out.println(memberAllergy.getAllergy().getId());
            memberAllergies.add(memberAllergy);
            memberAllergyRepository.save(memberAllergy);
        }
        member.setMemberAllergies(memberAllergies);

        pointService.givePointReward(member.getId(),"신규회원",BigDecimal.valueOf(1000));

        return MemberResDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .img(member.getMemberImg())
                .email(member.getEmail())
                .job(member.getJob().ordinal())
                .allergies(allergyIds)
                .point(pointService.getPointByMemberId(member.getId()))
                .build();
    }

    public MemberResDTO findMemberById(String memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<MemberAllergy> allergies = memberAllergyRepository.findAllByMemberId(memberId);
        List<Long> allergyIds = new ArrayList<>();
        for (MemberAllergy memberAllergy : allergies) {
            allergyIds.add(memberAllergy.getAllergy().getId());
        }
        return MemberResDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .img(member.getMemberImg())
                .email(member.getEmail())
                .job(member.getJob().ordinal())
                .allergies(allergyIds)
                .point(pointService.getPointByMemberId(member.getId()))
                .build();
    }

    public String getUserAssessToken(RestTemplate restTemplate, String code) {
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

    private String extractAccessToken(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, String> getUserInfo(RestTemplate restTemplate, String accessToken) {
        // Header Data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("charset", CHAR_SET);
        headers.set("Authorization", TOKEN_TYPE + accessToken );

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(USER_INFO_REQ_URL, HttpMethod.GET, requestEntity, String.class);

        String id = "kakao&" + extractValue(response.getBody(), "id");
        String nickname = extractValue(response.getBody(), "kakao_account", "profile", "nickname");
        String profileImageUrl = extractValue(response.getBody(), "kakao_account", "profile", "profile_image_url");
        String email = extractValue(response.getBody(), "kakao_account", "email");

        Map<String, String> info = new HashMap<>();
        info.put("id", id);
        info.put("name", nickname);
        info.put("imgUrl", profileImageUrl);
        info.put("email", email);

        return info;
    }

    private String extractValue(String responseBody, String... keys) {
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
