package likelion.babsim.domain.keyword;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="keyword_id")
    private Long id;
    private String keyword;
    private Long count;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
