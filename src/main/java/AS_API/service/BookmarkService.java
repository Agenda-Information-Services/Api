package AS_API.service;

import AS_API.dto.BookmarkDto;
import AS_API.entity.*;
import AS_API.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BillRepository billRepository;

    @Transactional
    public void addBookmark(Long userId, Long billId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("법안을 찾을 수 없습니다."));

        bookmarkRepository.findByUserAndBill(user, bill)
                .ifPresent(b -> {
                    throw new IllegalStateException("이미 북마크한 법안입니다.");
                });

        BillProposer proposer = bill.getProposer();
        if (proposer == null) {
            throw new IllegalArgumentException("법안에 발의자가 없습니다.");
        }

        Bookmark bookmark = Bookmark.builder()
                .user(user)
                .bill(bill)
                .proposer(proposer)
                .bookmarkedAt(LocalDateTime.now())
                .build();

        bookmarkRepository.save(bookmark);

        // 북마크 수 증가
        BillStatus billStatus = bill.getBillStatusDetail();
        if (billStatus != null) {
            billStatus.increaseBookmarkCount();
        }
    }

    @Transactional
    public void removeBookmark(Long userId, Long billId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new IllegalArgumentException("법안을 찾을 수 없습니다."));

        bookmarkRepository.deleteByUserAndBill(user, bill);

        // 북마크 수 감소
        BillStatus billStatus = bill.getBillStatusDetail();
        if (billStatus != null) {
            billStatus.decreaseBookmarkCount();
        }
    }

    public List<BookmarkDto> getBookmarksByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return bookmarkRepository.findAllByUser(user).stream()
                .map(bookmark -> BookmarkDto.builder()
                        .bookmarkId(bookmark.getBookmarkId())
                        .billId(bookmark.getBill().getBillId())
                        .billTitle(bookmark.getBill().getBillTitle()) // 법안 제목
                        .proposerName(bookmark.getProposer().getProposerName()) // 제안자 이름
                        .bookmarkedAt(bookmark.getBookmarkedAt())
                        .build())
                .collect(Collectors.toList());
    }

}
