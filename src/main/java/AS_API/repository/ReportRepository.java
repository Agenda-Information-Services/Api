package AS_API.repository;

import AS_API.entity.Report;
import AS_API.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    // reporter 전체 객체 기준으로 찾기
    List<Report> findByReporter(User reporter);
}
