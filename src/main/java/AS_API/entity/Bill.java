package AS_API.entity;

import AS_API.config.EmbeddingUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Bill")
@Getter
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "proposerId", nullable = false)
    private BillProposer proposer;

    @Column(nullable = false, length = 255)
    private String apiId;

    @Column(nullable = false)
    private Long billNumber;

    @Column(length = 255)
    private String billTitle;

    @Column(length = 255)
    private String billProposer;

    @Column(length = 255)
    private String committee;

    @Column(length = 255)
    private String billStatus;

    @Column
    private LocalDateTime billDate;

    @Column(columnDefinition = "TEXT")
    private String detail;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(columnDefinition = "TEXT")
    private String prediction;

    @Column(columnDefinition = "TEXT")
    private String term;

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    @JsonIgnore
    private BillStatus billStatusDetail;

    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition = "vector(1536)", nullable = false)
    private byte[] embedding;

    public void setEmbedding(float[] vector) {
        this.embedding = EmbeddingUtil.floatArrayToByteArray(vector);
    }

    public float[] getEmbedding() {
        if (embedding == null) return null;
        float[] vector = EmbeddingUtil.byteArrayToFloatArray(embedding);

        for (int i = 0; i < vector.length; i++) {
            if (Float.isNaN(vector[i]) || Float.isInfinite(vector[i])) {
                System.err.printf("⚠️ 잘못된 임베딩 값: index=%d, value=%f%n", i, vector[i]);
            }
        }

        return vector;
    }
}
