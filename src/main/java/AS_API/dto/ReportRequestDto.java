package AS_API.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportRequestDto {
    private Long postId;
    private String reason;

    @Builder
    public ReportRequestDto(Long postId, String reason) {
        this.postId = postId;
        this.reason = reason;
    }
}