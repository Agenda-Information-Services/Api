package AS_API.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String targetType;          // "USER", "POST", "COMMENT"
    private String reason;
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "reporter_id", referencedColumnName = "user_id")
    private User reporter;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "user_id")
    private User target; // 신고 대상이 사용자일 경우

    @ManyToOne
    @JoinColumn(name = "target_post_id")
    private Post targetPost; // 신고 대상이 게시글일 경우

    @ManyToOne
    @JoinColumn(name = "target_comment_id")
    private Comment targetComment; // 신고 대상이 댓글일 경우
}
