package AS_API.controller;

import AS_API.dto.BillDto;
import AS_API.dto.BillDetailDto;
import AS_API.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;

    @GetMapping("/search")
    public ResponseEntity<?> searchBills(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String proposer,
            @RequestParam(required = false) String detail,
            @RequestParam(required = false) String committee,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "billId"));
            Page<BillDto> bills = billService.searchBills(
                    (title != null && !title.trim().isEmpty()) ? title : null,
                    (proposer != null && !proposer.trim().isEmpty()) ? proposer : null,
                    (detail != null && !detail.trim().isEmpty()) ? detail : null,
                    (committee != null && !committee.trim().isEmpty()) ? committee : null,
                    pageable
            );
            return ResponseEntity.ok(bills);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("에러 발생: " + e.getMessage());
        }
    }

    @GetMapping("/{billId}")
    public ResponseEntity<?> getBillDetail(@PathVariable Long billId) {
        Optional<BillDetailDto> detail = billService.getBillDetail(billId);
        if (detail.isPresent()) {
            return ResponseEntity.ok(detail.get());
        } else {
            return ResponseEntity.status(404).body("법안을 찾을 수 없습니다.");
        }
    }
}
