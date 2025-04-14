package AS_API.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reporterName;
    private String reportContent;
    private String reportType;
    private LocalDateTime createdAt;

    protected Report() {
        // JPA 기본 생성자
    }

    public Report(String reporterName, String reportContent, String reportType) {
        this.reporterName = reporterName;
        this.reportContent = reportContent;
        this.reportType = reportType;
        this.createdAt = LocalDateTime.now();
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
