package AS_API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ReportedPostResponseDto {
    private Long reportId;
    private Long postId;
    private String reason;
    private LocalDateTime createdAt;
}