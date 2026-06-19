package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.CountryDto;
import Global.Holiday.Global.Holiday.admin.entities.Continent;
import Global.Holiday.Global.Holiday.admin.entities.Country;

public class CountryMapper {

    public static CountryDto toDTO(Country entity) {
        if (entity == null) return null;

        CountryDto dto = new CountryDto();
        dto.setCountryId(entity.getCountryId());

        dto.setContinentId(
                entity.getContinent() != null ? entity.getContinent().getContinentId() : null
        );

        dto.setCountryName(entity.getCountryName());
        dto.setCountryCode(entity.getCountryCode());
        dto.setCountryCodeIso3(entity.getCountryCodeIso3());
        dto.setCountryCodeNumeric(entity.getCountryCodeNumeric());
        dto.setCountryPhCode(entity.getCountryPhCode());
        dto.setCountryInternet(entity.getCountryInternet());
        dto.setActiveFlag(entity.getActiveFlag());

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdateDateTime(entity.getUpdateDateTime());

        return dto;
    }

    public static Country toEntity(CountryDto dto) {
        if (dto == null) return null;

        Country entity = new Country();
        entity.setCountryId(dto.getCountryId());

        if (dto.getContinentId() != null) {
            Continent continent = new Continent();
            continent.setContinentId(dto.getContinentId());
            entity.setContinent(continent);
        }

        entity.setCountryName(dto.getCountryName());
        entity.setCountryCode(dto.getCountryCode());
        entity.setCountryCodeIso3(dto.getCountryCodeIso3());
        entity.setCountryCodeNumeric(dto.getCountryCodeNumeric());
        entity.setCountryPhCode(dto.getCountryPhCode());
        entity.setCountryInternet(dto.getCountryInternet());
        entity.setActiveFlag(dto.getActiveFlag());

        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreateDateTime(dto.getCreateDateTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdateDateTime(dto.getUpdateDateTime());

        return entity;
    }
}
