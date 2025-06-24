package AS_API.dto;

import AS_API.entity.Comment;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommentTreeDto {

    private final Long commentId;
    private final Long postId;
    private final Long parentCommentId;
    private final Long userId; 
    private final String commentContent;
    private final LocalDateTime createdAt;
    private final List<CommentTreeDto> children = new ArrayList<>();

    public CommentTreeDto(Comment c) {
        this.commentId = c.getCommentId();
        this.postId = c.getPost().getPostId();            
        this.parentCommentId = c.getParentCommentId();
        this.userId = c.getUser().getUserId();             
        this.commentContent = c.getCommentContent();
        this.createdAt = c.getCreatedAt();
    }

    public Long getCommentId() { return commentId; }
    public Long getPostId() { return postId; }
    public Long getParentCommentId() { return parentCommentId; }
    public Long getUserId() { return userId; }      
    public String getCommentContent() { return commentContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public List<CommentTreeDto> getChildren() { return children; }
}
