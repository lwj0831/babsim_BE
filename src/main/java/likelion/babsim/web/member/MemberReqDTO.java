package likelion.babsim.web.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberReqDTO {
    private String id;
    private String name;
    private Integer age;
    private String email;
    private Integer job;
    private List<Long> allergy;
}
