package AS_API.service;

import AS_API.dto.ReportedPostResponseDto;
import AS_API.entity.Report;
import AS_API.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;
    private final UserManagementService userManagementService;

    public List<ReportedPostResponseDto> getReportedPosts() {
        return reportRepository.findAll().stream()
            .map(report -> new ReportedPostResponseDto(
                report.getId(),
                report.getPostId(),
                report.getReason(),
                report.getCreatedAt()))
            .collect(Collectors.toList());
    }

    public String getReportedPostUrl(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new RuntimeException("신고 정보 없음"));
        return "/posts/" + report.getPostId();
    }

    public void suspendUser(Long userId) {
        userManagementService.suspend(userId);
        System.out.println("User " + userId + " suspended.");
    }

    public void releaseUser(Long userId) {
        userManagementService.release(userId);
        System.out.println("User " + userId + " released.");
    }
}
