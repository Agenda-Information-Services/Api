package AS_API.dto;

public class ReportRequestDto {
    private final String reporterName;
    private final String reportContent;
    private final String reportType;

    public ReportRequestDto(String reporterName, String reportContent, String reportType) {
        this.reporterName = reporterName;
        this.reportContent = reportContent;
        this.reportType = reportType;
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
}