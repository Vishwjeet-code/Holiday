package Global.Holiday.Global.Holiday.admin.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryDto {
    private Long countryId;
    private Long continentId;

    private String countryName;
    private String countryCode;
    private String countryCodeIso3;
    private String countryCodeNumeric;
    private String countryPhCode;
    private String countryInternet;
    private Boolean activeFlag;

    private String createdBy;
    private LocalDateTime createDateTime;
    private String updatedBy;
    private LocalDateTime updateDateTime;

}
