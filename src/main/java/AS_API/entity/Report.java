package AS_API.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reporterId;
    private Long postId;
    private String reason;
    private LocalDateTime createdAt;

    @Builder
    public Report(Long reporterId, Long postId, String reason) {
        this.reporterId = reporterId;
        this.postId = postId;
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }
}
