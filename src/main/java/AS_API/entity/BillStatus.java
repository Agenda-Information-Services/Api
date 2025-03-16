package AS_API.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BillStatus")
public class BillStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @OneToOne
    @JoinColumn(name = "billId", nullable = false)
    private Bill bill;

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
}
