package AS_API.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReportRequestDto {
    private Long targetId;
    private String targetType;
    private String reason;
}
