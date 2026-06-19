package Global.Holiday.Global.Holiday.enkreateclient.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "project_details", schema = "public")
public class ProjectDetails {

    @Id
    @Column(name = "project_details_id")
    private Long projectDetailsId;

    @Column(name = "industry_id")
    private Long industryId;

    @Column(name = "project_office_location")
    private String projectOfficeLocation;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_contract_no")
    private String projectContractNo;

    @Column(name = "project_date")
    private LocalDate projectDate;

    @Column(name = "project_address1")
    private String projectAddress1;

    @Column(name = "project_address2")
    private String projectAddress2;

    @Column(name = "project_city_id")
    private Long projectCityId;

    @Column(name = "project_state_id")
    private Long projectStateId;

    @Column(name = "project_city_pin_id")
    private Long projectCityPinId;

    @Column(name = "project_phone1")
    private String projectPhone1;

    @Column(name = "project_phone2")
    private String projectPhone2;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    public ProjectDetails() {

    }
}
