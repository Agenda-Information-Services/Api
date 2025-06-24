package AS_API.controller;

import AS_API.config.EmbeddingCache;
import AS_API.dto.BillDto;
import AS_API.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;
    private final EmbeddingCache embeddingCache;

    @PostMapping
    public ResponseEntity<?> recommend(@RequestBody Map<String, String> request) {
        try {
            String input = request.get("query");

            if (input == null || input.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("입력값 'query'가 필요합니다.");
            }

            int limit = 5;
            List<BillDto> results = recommendationService.recommendByText(input, limit);

            return ResponseEntity.ok(results != null ? results : Collections.emptyList());

        } catch (Exception e) {
            return ResponseEntity.status(500).body("추천 처리 중 오류: " + e.getMessage());
        }
    }

    @PostMapping("/user")
    public ResponseEntity<?> recommendForUser(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        if (userId == null) {
            return ResponseEntity.badRequest().body("userId는 필수입니다.");
        }

        int limit = 10;
        List<BillDto> results = recommendationService.recommendForUser(userId, limit);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/refresh-cache")
    public ResponseEntity<?> refreshEmbeddingCache() {
        embeddingCache.reloadCache();
        return ResponseEntity.ok("추천 임베딩 캐시가 갱신되었습니다.");
    }

}
