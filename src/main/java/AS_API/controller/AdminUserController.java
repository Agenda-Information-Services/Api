package AS_API.controller;

import AS_API.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminService adminService;

    @PutMapping("/suspend/{userId}")
    public ResponseEntity<String> suspendUser(@PathVariable Long userId) {
        adminService.suspendUser(userId);
        return ResponseEntity.ok("✅ 사용자 활동이 정지되었습니다.");
    }

    @PutMapping("/resume/{userId}")
    public ResponseEntity<String> resumeUser(@PathVariable Long userId) {
        adminService.resumeUser(userId);
        return ResponseEntity.ok("✅ 사용자 활동 정지가 해제되었습니다.");
    }
}
