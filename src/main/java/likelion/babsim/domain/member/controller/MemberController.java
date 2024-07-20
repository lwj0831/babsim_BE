package likelion.babsim.domain.member.controller;

import likelion.babsim.domain.member.service.MemberService;
import likelion.babsim.web.member.MemberReqDTO;
import likelion.babsim.web.member.MemberResDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public MemberResDTO createMember(@RequestBody MemberReqDTO memberRequestDTO) {
        return memberService.createMember(memberRequestDTO);
    }

    @GetMapping("/{memberId}")
    public MemberResDTO getMember(@PathVariable String memberId) {
        return memberService.findMemberById(memberId);
    }
}
