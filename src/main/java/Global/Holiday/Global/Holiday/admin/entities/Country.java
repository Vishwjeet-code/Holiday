package Global.Holiday.Global.Holiday.admin.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@Entity
@Table(name = "country", schema = "public")
public class Country {

    @Id
    @Column(name = "country_id")
    private Long countryId;

    // 🔗 Relationship (assuming Continent entity exists)
    @ManyToOne
    @JoinColumn(name = "continent_id")
    private Continent continent;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "country_code_iso3")
    private String countryCodeIso3;

    @Column(name = "country_code_numeric")
    private String countryCodeNumeric;

    @Column(name = "country_ph_code")
    private String countryPhCode;

    @Column(name = "country_internet")
    private String countryInternet;

    @Column(name = "active_flag")
    private Boolean activeFlag;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;

    // Constructors
    public Country() {}

    // Getters & Setters
    public Long getCountryId() {
        return countryId;
    }

    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCodeIso3() {
        return countryCodeIso3;
    }

    public void setCountryCodeIso3(String countryCodeIso3) {
        this.countryCodeIso3 = countryCodeIso3;
    }

    public String getCountryCodeNumeric() {
        return countryCodeNumeric;
    }

    public void setCountryCodeNumeric(String countryCodeNumeric) {
        this.countryCodeNumeric = countryCodeNumeric;
    }

    public String getCountryPhCode() {
        return countryPhCode;
    }

    public void setCountryPhCode(String countryPhCode) {
        this.countryPhCode = countryPhCode;
    }

    public String getCountryInternet() {
        return countryInternet;
    }

    public void setCountryInternet(String countryInternet) {
        this.countryInternet = countryInternet;
    }

    public Boolean getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(Boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(LocalDateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }
}
