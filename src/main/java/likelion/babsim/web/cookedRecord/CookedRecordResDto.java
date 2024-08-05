package likelion.babsim.web.cookedRecord;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CookedRecordResDto {
    private Long id;
    private Long cookedCount;

    @Builder
    public CookedRecordResDto(Long id, Long cookedCount) {
        this.id = id;
        this.cookedCount = cookedCount;
    }
}
