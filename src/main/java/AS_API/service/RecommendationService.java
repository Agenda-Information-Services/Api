package AS_API.service;

import AS_API.config.EmbeddingCache;
import AS_API.dto.BillDto;
import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import AS_API.repository.BookmarkRepository;
import AS_API.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final OpenAiEmbeddingService embeddingService;
    private final EmbeddingCache embeddingCache;
    private final BillRepository billRepository;

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;

    public List<BillDto> recommendByText(String text, int limit) throws IOException, InterruptedException {
        float[] query = embeddingService.getNormalizedEmbedding(text);
        float[][] vectors = embeddingCache.getVectors();
        List<Long> ids = embeddingCache.getIds();

        List<Score> top = new ArrayList<>();
        for (int i = 0; i < vectors.length; i++) {
            float[] v = vectors[i];
            double score = dot(query, v);
            if (score >= 0.1) {
                top.add(new Score(ids.get(i), score));
            }
        }

        top.sort(Comparator.comparingDouble(Score::score).reversed());

        List<Long> topIds = top.stream()
                .limit(limit)
                .map(Score::id)
                .toList();

        List<Bill> topBills = billRepository.findAllById(topIds);

        Map<Long, Bill> billMap = new HashMap<>();
        for (Bill bill : topBills) {
            billMap.put(bill.getBillId(), bill);
        }

        List<BillDto> result = new ArrayList<>();
        for (Long id : topIds) {
            Bill b = billMap.get(id);
            if (b != null) result.add(toDto(b));
        }

        return result;
    }

    private double dot(float[] a, float[] b) {
        double sum = 0;
        for (int i = 0; i < a.length; i++) sum += a[i] * b[i];
        return sum;
    }

    private BillDto toDto(Bill bill) {
        return new BillDto(
                bill.getBillId(),
                bill.getApiId(),
                bill.getBillNumber(),
                bill.getBillTitle(),
                bill.getBillProposer(),
                bill.getCommittee(),
                bill.getBillStatus(),
                bill.getProposer().getPoly()
        );
    }

    private record Score(Long id, double score) {}


    public List<BillDto> recommendForUser(Long userId, int limit) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        var bookmarks = bookmarkRepository.findAllByUser(user);
        if (bookmarks.isEmpty()) return List.of();

        List<float[]> vectors = bookmarks.stream()
                .map(b -> b.getBill().getEmbedding())
                .filter(Objects::nonNull)
                .toList();
        float[] userVector = averageVector(vectors);

        Set<Long> bookmarkedIds = bookmarks.stream()
                .map(b -> b.getBill().getBillId())
                .collect(Collectors.toSet());

        float[][] allVectors = embeddingCache.getVectors();
        List<Long> allIds = embeddingCache.getIds();
        PriorityQueue<Score> pq = new PriorityQueue<>(Comparator.comparingDouble(Score::score));

        for (int i = 0; i < allVectors.length; i++) {
            Long id = allIds.get(i);
            if (bookmarkedIds.contains(id)) continue;

            double score = dot(userVector, allVectors[i]);
            pq.offer(new Score(id, score));
            if (pq.size() > limit) pq.poll();
        }

        List<Long> topIds = new ArrayList<>();
        while (!pq.isEmpty()) {
            topIds.add(pq.poll().id());
        }
        Collections.reverse(topIds);

        List<Bill> topBills = billRepository.findAllById(topIds);
        Map<Long, Bill> billMap = topBills.stream().collect(Collectors.toMap(Bill::getBillId, b -> b));

        return topIds.stream()
                .map(billMap::get)
                .filter(Objects::nonNull)
                .map(this::toDto)
                .toList();
    }


    private float[] averageVector(List<float[]> vectors) {
        if (vectors == null || vectors.isEmpty()) {
            throw new IllegalArgumentException("평균 벡터를 계산할 벡터 리스트가 비어있습니다.");
        }

        int len = vectors.get(0).length;
        float[] avg = new float[len];

        for (float[] vec : vectors) {
            for (int i = 0; i < len; i++) {
                avg[i] += vec[i];
            }
        }

        for (int i = 0; i < len; i++) {
            avg[i] /= vectors.size();
        }

        return avg;
    }
}
