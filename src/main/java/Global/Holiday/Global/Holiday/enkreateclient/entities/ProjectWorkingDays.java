package Global.Holiday.Global.Holiday.enkreateclient.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "projectworkingdays",schema = "public")
public class ProjectWorkingDays {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_working_days_seq_gen")
    @SequenceGenerator(
            name = "project_working_days_seq_gen",
            sequenceName = "project_working_days_seq",
            allocationSize = 1,
            initialValue = 88001
    )
    @Column(name = "project_working_day_id")
    private Long projectWorkingDayId;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "day_id", nullable = false)
    private Long dayId;

    @Column(name = "working_flag", nullable = false)
    private char workingFlag;

    @Column(name = "work_start_time")
    private LocalTime workStartTime;

    @Column(name = "work_end_time")
    private LocalTime workEndTime;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;




}
