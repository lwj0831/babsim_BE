package likelion.babsim.web.member;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberResDTO {
    private String id;
    private String name;
    private String img;
    private String email;
    private Integer job;
    private List<Long> allergies;
    private Integer point;

    @Builder
    public MemberResDTO(String id, String name, String img, String email, Integer job, List<Long> allergies, Integer point) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.email = email;
        this.job = job;
        this.allergies = allergies;
        this.point = point;
    }
}
