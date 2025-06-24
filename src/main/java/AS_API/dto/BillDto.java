package AS_API.dto;

import lombok.Getter;

@Getter
public class BillDto {
    private Long billId;
    private String apiId;
    private Long billNumber;
    private String billTitle;
    private String billProposer;
    private String committee;
    private String billStatus;
    private String poly;

    public BillDto(Long billId, String apiId, Long billNumber, String billTitle,
                   String billProposer, String committee, String billStatus, String poly) {
        this.billId = billId;
        this.apiId = apiId;
        this.billNumber = billNumber;
        this.billTitle = billTitle;
        this.billProposer = billProposer;
        this.committee = committee;
        this.billStatus = billStatus;
        this.poly = poly;
    }
}

