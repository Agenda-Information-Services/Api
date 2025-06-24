package AS_API.repository;

import AS_API.entity.Bookmark;
import AS_API.entity.User;
import AS_API.entity.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByUserAndBill(User user, Bill bill);
    List<Bookmark> findAllByUser(User user);
    void deleteByUserAndBill(User user, Bill bill);
}
