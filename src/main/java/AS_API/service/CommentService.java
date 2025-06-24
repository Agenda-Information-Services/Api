package AS_API.service;

import AS_API.dto.CommentRequestDto;
import AS_API.dto.CommentTreeDto;
import AS_API.entity.Comment;
import AS_API.entity.Post;
import AS_API.entity.User;
import AS_API.repository.CommentRepository;
import AS_API.repository.PostRepository;
import AS_API.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Comment createComment(Long userId, CommentRequestDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        Post post = postRepository.findById(dto.getPostId())
            .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
            .user(user)
            .post(post)
            .parentCommentId(dto.getParentCommentId())
            .commentContent(dto.getCommentContent())
            .build();

        return commentRepository.save(comment);  
    }

    public List<CommentTreeDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);
        List<CommentTreeDto> flatList = comments.stream()
            .map(CommentTreeDto::new)
            .collect(Collectors.toList());

        return buildCommentTree(flatList);
    }

    private List<CommentTreeDto> buildCommentTree(List<CommentTreeDto> flatList) {
        Map<Long, CommentTreeDto> map = new HashMap<>();
        List<CommentTreeDto> roots = new ArrayList<>();

        for (CommentTreeDto dto : flatList) {
            map.put(dto.getCommentId(), dto);
        }

        for (CommentTreeDto dto : flatList) {
            Long parentId = dto.getParentCommentId();
            if (parentId == null) {
                roots.add(dto);
            } else {
                CommentTreeDto parent = map.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }

        return roots;
    }

    @Transactional
    public void updateComment(Long commentId, Long userId, CommentRequestDto dto) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        comment.update(dto.getCommentContent());
    }

    @Transactional
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));

        if (!comment.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
