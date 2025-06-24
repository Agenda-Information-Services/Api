package AS_API.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class PostResponseDto {

    private final Long postId;
    private final String postTitle;
    private final String content;
    private final int postCount;
    private final LocalDateTime postDate;

    @Builder
    public PostResponseDto(Long postId, String postTitle, String content, int postCount, LocalDateTime postDate) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.content = content;
        this.postCount = postCount;
        this.postDate = postDate;
    }

    public Long getPostId() { return postId; }
    public String getPostTitle() { return postTitle; }
    public String getContent() { return content; }
    public int getPostCount() { return postCount; }
    public LocalDateTime getPostDate() { return postDate; }
}
