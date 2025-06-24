package AS_API.service;

import AS_API.dto.BillDto;
import AS_API.dto.BillDetailDto;
import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import AS_API.repository.BillStatusRepository;
import AS_API.entity.BillStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final BillStatusRepository billStatusRepository;

    public Page<BillDto> searchBills(String title, String proposer, String detail, String committee, Pageable pageable) {
        return billRepository.searchBills(
                (title == null || title.trim().isEmpty()) ? null : title,
                (proposer == null || proposer.trim().isEmpty()) ? null : proposer,
                (detail == null || detail.trim().isEmpty()) ? null : detail,
                (committee == null || committee.trim().isEmpty()) ? null : committee,
                pageable
        );
    }

    @Transactional
    public Optional<BillDetailDto> getBillDetail(Long billId) {
        billStatusRepository.findByBill_BillId(billId)
                .ifPresent(BillStatus::increaseBillCount);
        return billRepository.findBillDetailById(billId);
    }

    public List<Bill> findSimilarBills(float[] queryVector, int limit) {
        List<Bill> candidates = billRepository.findTopCandidates(1000); // 필터 조건 없을 경우 전체 중 일부
        return candidates.stream()
                .map(bill -> {
                    float[] stored = bill.getEmbedding();
                    float score = 0f;
                    for (int i = 0; i < stored.length; i++) {
                        score += stored[i] * queryVector[i]; // dot product
                    }
                    return new BillScore(bill, score);
                })
                .sorted((a, b) -> Float.compare(b.score, a.score))
                .limit(limit)
                .map(BillScore::bill)
                .toList();
    }

    private static class BillScore {
        Bill bill;
        float score;
        BillScore(Bill bill, float score) {
            this.bill = bill;
            this.score = score;
        }
        public Bill bill() {
            return bill;
        }
    }

}
