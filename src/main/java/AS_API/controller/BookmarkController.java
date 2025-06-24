package AS_API.controller;

import AS_API.dto.BookmarkDto;
import AS_API.entity.User;
import AS_API.service.BookmarkService;
import AS_API.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final UserService userService;

    // 북마크 추가
    @PostMapping("/{billId}")
    public ResponseEntity<String> addBookmark(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long billId) {

        User user = userService.findUserByUid(userDetails.getUsername());
        bookmarkService.addBookmark(user.getUserId(), billId);
        return ResponseEntity.ok("북마크가 추가되었습니다.");
    }

    // 북마크 삭제
    @DeleteMapping("/{billId}")
    public ResponseEntity<String> removeBookmark(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long billId) {

        User user = userService.findUserByUid(userDetails.getUsername());
        bookmarkService.removeBookmark(user.getUserId(), billId);
        return ResponseEntity.ok("북마크가 삭제되었습니다.");
    }

    @GetMapping
    public ResponseEntity<List<BookmarkDto>> getBookmarks(
            @AuthenticationPrincipal UserDetails userDetails) {

        User user = userService.findUserByUid(userDetails.getUsername());
        List<BookmarkDto> bookmarks = bookmarkService.getBookmarksByUser(user.getUserId());
        return ResponseEntity.ok(bookmarks);
    }
}
