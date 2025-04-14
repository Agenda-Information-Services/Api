package AS_API.service;

import AS_API.dto.ReportRequestDto;
import AS_API.dto.ReportResponseDto;
import AS_API.entity.Report;
import AS_API.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void createReport(ReportRequestDto requestDto) {
        Report report = new Report(
                requestDto.getReporterName(),
                requestDto.getReportContent(),
                requestDto.getReportType()
        );
        reportRepository.save(report);
    }

    public List<ReportResponseDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(ReportResponseDto::new)
                .collect(Collectors.toList());
    }
}
