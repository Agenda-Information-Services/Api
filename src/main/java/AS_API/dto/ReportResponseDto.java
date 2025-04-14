package AS_API.dto;


import AS_API.entity.Report;
import java.time.LocalDateTime;

public class ReportResponseDto {

    private final Long id;
    private final String reporterName;
    private final String reportContent;
    private final String reportType;
    private final LocalDateTime createdAt;

    public ReportResponseDto(Report report) {
        this.id = report.getId();
        this.reporterName = report.getReporterName();
        this.reportContent = report.getReportContent();
        this.reportType = report.getReportType();
        this.createdAt = report.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReportContent() {
        return reportContent;
    }

    public String getReportType() {
        return reportType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
