package AS_API.controller;

import AS_API.dto.ReportedPostResponseDto;
import AS_API.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/list")
    public ResponseEntity<List<ReportedPostResponseDto>> getReportedPosts() {
        return ResponseEntity.ok(reportService.getReportedPosts());
    }

    @GetMapping("/redirect/{reportId}")
    public ResponseEntity<Void> redirectToReportedPost(@PathVariable Long reportId) {
        String redirectUrl = reportService.getReportedPostUrl(reportId);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    @PutMapping("/suspend/{userId}")
    public ResponseEntity<Void> suspendUser(@PathVariable Long userId) {
        reportService.suspendUser(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/release/{userId}")
    public ResponseEntity<Void> releaseUser(@PathVariable Long userId) {
        reportService.releaseUser(userId);
        return ResponseEntity.ok().build();
    }
}
