package AS_API.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "BillProposer")
public class BillProposer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long proposerId;

    @ManyToOne
    @JoinColumn(name = "billId", nullable = false)
    private Bill bill;

    @Column(nullable = true, length = 255)
    private String proposerName;

    @Column(nullable = true)
    private String bth;

    @Column(nullable = true, length = 255)
    private String job;

    @Column(nullable = true, length = 255)
    private String poly;

    @Column(nullable = true, length = 255)
    private String orig;

    @Column(nullable = true, length = 255)
    private String cmits;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String memTitle;
}
