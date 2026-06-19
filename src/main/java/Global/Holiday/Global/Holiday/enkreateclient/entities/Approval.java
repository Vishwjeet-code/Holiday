package Global.Holiday.Global.Holiday.enkreateclient.entities;



import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Approval",schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Approval {

    @Id
    @Column(nullable = false)
    private Long approval_Status_Id;

    @Column(name = "approval_status_name", nullable = false, unique = true, length = 50)
    private String status;  // e.g. "Saved", "PendingApproval"

    @Column(name = "approval_status_desc", nullable = false, length = 255)
    private String description; // e.g. "Saved but not sent for approval"

    @Column(name = "created_by", nullable = false, length = 100)
    private String createdBy;

    @Column(name = "create_date_time", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_by", nullable = false, length = 100)
    private String updatedBy;

    @Column(name = "update_date_time", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
