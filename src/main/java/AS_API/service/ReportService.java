package AS_API.service;

import AS_API.dto.ReportRequestDto;
import AS_API.entity.*;
import AS_API.exception.CustomException;
import AS_API.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static AS_API.exception.ErrorCode.REPORT_NOT_FOUND;
import static AS_API.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Report createReport(Long reporterId, ReportRequestDto dto) {
        User reporter = userRepository.findById(reporterId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Report.ReportBuilder builder = Report.builder()
                .reporter(reporter)
                .targetType(dto.getTargetType())
                .reason(dto.getReason())
                .createdAt(LocalDateTime.now());

        switch (dto.getTargetType().toUpperCase()) {
            case "USER":
                User targetUser = userRepository.findById(dto.getTargetId())
                        .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
                builder.target(targetUser);
                break;

            case "POST":
                Post targetPost = postRepository.findById(dto.getTargetId())
                        .orElseThrow(() -> new RuntimeException("해당 게시글이 존재하지 않습니다."));
                builder.targetPost(targetPost);
                break;

            case "COMMENT":
                Comment targetComment = commentRepository.findById(dto.getTargetId())
                        .orElseThrow(() -> new RuntimeException("해당 댓글이 존재하지 않습니다."));
                builder.targetComment(targetComment);
                break;

            default:
                throw new IllegalArgumentException("지원하지 않는 신고 유형입니다.");
        }

        return reportRepository.save(builder.build());
    }

    public List<Report> getReportsByUser(Long userId) {
        User reporter = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        return reportRepository.findByReporter(reporter);
    }

    public Report getReportById(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new CustomException(REPORT_NOT_FOUND));
    }
}
