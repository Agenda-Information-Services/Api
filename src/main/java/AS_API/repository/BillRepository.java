package AS_API.repository;

import AS_API.dto.BillDetailDto;
import AS_API.dto.BillDto;
import AS_API.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {

    @Query("SELECT new AS_API.dto.BillDto(b.billId, b.apiId, b.billNumber, b.billTitle, " +
            "b.billProposer, b.committee, b.billStatus, b.proposer.poly) " +
            "FROM Bill b " +
            "WHERE (:title IS NULL OR b.billTitle LIKE CONCAT('%', :title, '%')) " +
            "AND (:proposer IS NULL OR b.billProposer LIKE CONCAT('%', :proposer, '%')) " +
            "AND (:detail IS NULL OR b.detail LIKE CONCAT('%', :detail, '%')) " +
            "AND (:committee IS NULL OR b.committee LIKE CONCAT('%', :committee, '%'))")
    Page<BillDto> searchBills(@Param("title") String title,
                              @Param("proposer") String proposer,
                              @Param("detail") String detail,
                              @Param("committee") String committee,
                              Pageable pageable);

    @Query("SELECT new AS_API.dto.BillDetailDto(" +
            "b.billId, b.apiId, b.billNumber, b.billTitle, b.billProposer, b.committee, b.billStatus, " +
            "b.billDate, b.detail, b.summary, b.prediction, b.term, " +
            "bs.yes, bs.no, bs.bookmarkCount, bp.poly) " +
            "FROM Bill b " +
            "JOIN b.billStatusDetail bs " +
            "JOIN b.proposer bp " +
            "WHERE b.billId = :billId")
    Optional<BillDetailDto> findBillDetailById(@Param("billId") Long billId);

    @Query(value = "SELECT * FROM Bill LIMIT :limit", nativeQuery = true)
    List<Bill> findTopCandidates(@Param("limit") int limit);
}
