package likelion.babsim.domain.keyword;

import jakarta.persistence.*;
import lombok.*;

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

    @Builder
    public Keyword(String keyword, Long count) {
        this.keyword = keyword;
        this.count = count;
    }

    public void increaseCount(){
        count++;
    }
}
