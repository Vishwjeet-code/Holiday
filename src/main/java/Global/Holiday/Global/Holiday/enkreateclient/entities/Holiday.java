package Global.Holiday.Global.Holiday.enkreateclient.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "holiday", schema = "public")
@Data
public class Holiday {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "holiday_id")
        private Long holidayId;

        @Column(name = "date_id")
        private Long dateId;

        @Column(name = "country_id")
        private Long countryId;

        @Column(name = "year_id")
        private Long yearId;

        @Column(name = "holiday_name")
        private String holidayName;

        @Column(name = "day")
        private String day;

        @Column(name = "comment")
        private String comment;

        @Column(name = "created_by")
        private String createdBy;

        @Column(name = "create_date_time")
        private LocalDateTime createDate;

        @Column(name = "updated_by")
        private String updatedBy;

        @Column(name = "update_date_time")
        private LocalDateTime updateDate;
}



