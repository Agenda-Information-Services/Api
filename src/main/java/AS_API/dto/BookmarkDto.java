package AS_API.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BookmarkDto {
    private Long bookmarkId;
    private Long billId;
    private String billTitle;
    private String proposerName;
    private LocalDateTime bookmarkedAt;
}
