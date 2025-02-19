package likelion.babsim.web.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoLoginResDto {
    private String id;
    private String name;
    private String email;
    private String img;
}
