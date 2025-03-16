package AS_API.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @Column(nullable = false, length = 255)
    private String apiId;

    @Column(nullable = false)
    private Long billNumber;

    @Column(nullable = true, length = 255)
    private String billTitle;

    @Column(nullable = true, length = 255)
    private String billProposer;

    @Column(nullable = true, length = 255)
    private String committee;

    @Column(nullable = true, length = 255)
    private String billStatus;

    @Column(nullable = true)
    private LocalDateTime billDate;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String detail;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String summary;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String prediction;

    @OneToOne(mappedBy = "bill", cascade = CascadeType.ALL)
    private BillStatus billStatusDetail;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillProposer> proposers;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<Bookmark> bookmarks;
}