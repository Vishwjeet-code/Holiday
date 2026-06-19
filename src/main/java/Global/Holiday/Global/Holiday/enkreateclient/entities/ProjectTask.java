package Global.Holiday.Global.Holiday.enkreateclient.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_task")
@Data
public class ProjectTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "mpp_task_id")
    private Long mppTaskId;

    @Column(name = "task_name", length = 500)
    private String taskName;

    @Column(name = "duration", length = 100)
    private String duration;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @Column(name = "predecessors", length = 200)
    private String predecessors;

    @Column(name = "resource_names", length = 500)
    private String resourceNames;

    @Column(name = "outline_level")
    private Integer outlineLevel;

    @Column(name = "parent_task_id")
    private Long parentTaskId;

}
