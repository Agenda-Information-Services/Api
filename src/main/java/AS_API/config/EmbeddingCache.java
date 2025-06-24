package AS_API.config;

import AS_API.entity.Bill;
import AS_API.repository.BillRepository;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class EmbeddingCache {

    private final BillRepository billRepository;

    @Getter
    private List<Long> ids = new ArrayList<>();

    @Getter
    private float[][] vectors;

    @PostConstruct
    public void preload() {
        reloadCache();
    }

    public synchronized void reloadCache() {
        List<Bill> bills = billRepository.findAll();

        List<Long> newIds = new ArrayList<>();
        List<float[]> vectorList = new ArrayList<>();

        for (Bill bill : bills) {
            float[] emb = bill.getEmbedding();
            if (emb != null && isValid(emb)) {
                newIds.add(bill.getBillId());
                vectorList.add(normalize(emb));
            }
        }

        this.ids = newIds;
        this.vectors = vectorList.toArray(new float[0][0]);
        System.out.printf("캐시 강제 갱신 완료: %d개 임베딩\n", ids.size());
    }

    private boolean isValid(float[] v) {
        for (float f : v) {
            if (Float.isNaN(f) || Float.isInfinite(f)) return false;
        }
        return true;
    }

    private float[] normalize(float[] v) {
        float norm = 0f;
        for (float f : v) norm += f * f;
        norm = (float) Math.sqrt(norm);
        if (norm == 0f) return v;
        float[] result = new float[v.length];
        for (int i = 0; i < v.length; i++) result[i] = v[i] / norm;
        return result;
    }
}
