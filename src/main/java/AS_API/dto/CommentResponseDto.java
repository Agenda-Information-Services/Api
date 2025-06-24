package AS_API.dto;

import AS_API.entity.Comment;
import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponseDto {

    private final Long commentId;
    private final Long postId;
    private final Long parentCommentId;
    private final String commentContent;
    private final LocalDateTime createdAt;

    @Builder
    public CommentResponseDto(Long commentId, Long postId, Long parentCommentId,
                              String commentContent, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.commentContent = commentContent;
        this.createdAt = createdAt;
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.postId = comment.getPost().getPostId();
        this.parentCommentId = comment.getParentCommentId();
        this.commentContent = comment.getCommentContent();
        this.createdAt = comment.getCreatedAt();
    }

    public Long getCommentId() { return commentId; }
    public Long getPostId() { return postId; }
    public Long getParentCommentId() { return parentCommentId; }
    public String getCommentContent() { return commentContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
