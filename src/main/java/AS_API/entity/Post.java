package AS_API.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String postTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int postCount;

    private LocalDateTime postDate;

    public void increaseViewCount() {
        this.postCount += 1;
    }

    public void update(String postTitle, String content) {
        this.postTitle = postTitle;
        this.content = content;
    }

    @PrePersist
    public void onCreate() {
        this.postDate = LocalDateTime.now();
    }
}
