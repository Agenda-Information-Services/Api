package AS_API.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BillDetailDto {
    private Long billId;
    private String apiId;
    private Long billNumber;
    private String billTitle;
    private String billProposer;
    private String committee;
    private String billStatus;
    private LocalDateTime billDate;
    private String detail;
    private String summary;
    private String prediction;
    private String term;
    private int yes;
    private int no;
    private int bookmarkCount;
    private String poly;
}
