package AS_API.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BillStatus")
@NoArgsConstructor
@Getter
public class BillStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "billId", nullable = false)
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "proposerId", nullable = false)
    private BillProposer proposer;

    @Column(nullable = false)
    private int billCount;

    @Column(nullable = true, length = 500)
    private String link;

    @Column(nullable = false)
    private int yes;

    @Column(nullable = false)
    private int no;

    @Column(nullable = false)
    private int bookmarkCount;

    public void update(int yes, int no) {
        this.yes = yes;
        this.no = no;
    }

    // 북마크 수 증가
    public void increaseBookmarkCount() {
        this.bookmarkCount += 1;
    }

    // 북마크 수 감소
    public void decreaseBookmarkCount() {
        if (this.bookmarkCount > 0) {
            this.bookmarkCount -= 1;
        }
    }

    public void increaseBillCount() {
        this.billCount += 1;
    }
}


