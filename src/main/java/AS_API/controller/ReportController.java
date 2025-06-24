package AS_API.controller;

import AS_API.dto.ReportRequestDto;
import AS_API.entity.Report;
import AS_API.service.ReportService;
import AS_API.config.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<?> createReport(@RequestBody ReportRequestDto reportRequestDto,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || userDetails.getUser() == null) {
            return ResponseEntity.status(401).body("로그인이 필요한 요청입니다.");
        }

        Long reporterId = userDetails.getUser().getUserId();
        Report report = reportService.createReport(reporterId, reportRequestDto);
        return ResponseEntity.ok("신고가 생성되었습니다. 신고 ID: " + report.getId());
    }
}
