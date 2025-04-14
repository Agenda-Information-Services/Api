package AS_API.controller;

import AS_API.dto.ReportRequestDto;
import AS_API.dto.ReportResponseDto;
import AS_API.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public void createReport(@RequestBody ReportRequestDto requestDto) {
        reportService.createReport(requestDto);
    }

    @GetMapping("/list")
    public List<ReportResponseDto> getAllReports() {
        return reportService.getAllReports();
    }
}
