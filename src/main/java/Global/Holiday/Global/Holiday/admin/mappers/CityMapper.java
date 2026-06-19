package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.CityDto;
import Global.Holiday.Global.Holiday.admin.entities.City;
import Global.Holiday.Global.Holiday.admin.entities.Country;
import Global.Holiday.Global.Holiday.admin.entities.State;

public class CityMapper {

    public static CityDto toDTO(City entity) {
        if (entity == null) return null;

        CityDto dto = new CityDto();
        dto.setCityId(entity.getCityId());
        dto.setCityName(entity.getCityName());

        dto.setStateId(
                entity.getState() != null ? entity.getState().getStateId() : null
        );

        dto.setCountryId(
                entity.getCountry() != null ? entity.getCountry().getCountryId() : null
        );

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdateDateTime(entity.getUpdateDateTime());

        return dto;
    }

    public static City toEntity(CityDto dto) {
        if (dto == null) return null;

        City entity = new City();
        entity.setCityId(dto.getCityId());
        entity.setCityName(dto.getCityName());

        if (dto.getStateId() != null) {
            State state = new State();
            state.setStateId(dto.getStateId());
            entity.setState(state);
        }

        if (dto.getCountryId() != null) {
            Country country = new Country();
            country.setCountryId(dto.getCountryId());
            entity.setCountry(country);
        }

        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreateDateTime(dto.getCreateDateTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdateDateTime(dto.getUpdateDateTime());

        return entity;
    }
}
