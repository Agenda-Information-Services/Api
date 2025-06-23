package AS_API.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostRequestDto {

    private final String postTitle;
    private final String content;

    @JsonCreator
    public PostRequestDto(
        @JsonProperty("postTitle") String postTitle,
        @JsonProperty("content") String content
    ) {
        this.postTitle = postTitle;
        this.content = content;
    }

    public String getPostTitle() { return postTitle; }
    public String getContent() { return content; }
}
