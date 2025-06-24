package AS_API.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentRequestDto {

    private final Long postId;
    private final Long parentCommentId;
    private final String commentContent;

    @JsonCreator
    public CommentRequestDto(
        @JsonProperty("postId") Long postId,
        @JsonProperty("parentCommentId") Long parentCommentId,
        @JsonProperty("commentContent") String commentContent
    ) {
        this.postId = postId;
        this.parentCommentId = parentCommentId;
        this.commentContent = commentContent;
    }

    public Long getPostId() { return postId; }
    public Long getParentCommentId() { return parentCommentId; }
    public String getCommentContent() { return commentContent; }
}
