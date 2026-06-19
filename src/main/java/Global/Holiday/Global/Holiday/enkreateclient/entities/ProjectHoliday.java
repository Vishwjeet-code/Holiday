package Global.Holiday.Global.Holiday.enkreateclient.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "projectholiday", schema = "public")
@Data
public class ProjectHoliday {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "project_holiday_seq" )
    @SequenceGenerator(
            name      = "project_holiday_seq",
            sequenceName = "project_holiday_seq",
            initialValue = 770000,      // ✅ starts from here
            allocationSize = 1          // ✅ increments by 1
    )
    @Column(name = "project_holiday_id")
    private Long projectHolidayId;

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "industry_id")
    private Long industryId;

    @Column(name = "holiday_id")
    private Long holidayId; // global

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date_time")
    private LocalDateTime createDate;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_date_time")
    private LocalDateTime updateDate;

    @Column(name = "status_id")
    private Long statusId;



}
