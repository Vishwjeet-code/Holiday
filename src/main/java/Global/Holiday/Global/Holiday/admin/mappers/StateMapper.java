package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.StateDto;
import Global.Holiday.Global.Holiday.admin.entities.Country;
import Global.Holiday.Global.Holiday.admin.entities.State;

public class StateMapper {

    public static StateDto toDTO(State entity) {
        if (entity == null) return null;

        StateDto dto = new StateDto();
        dto.setStateId(entity.getStateId());
        dto.setStateCode(entity.getStateCode());
        dto.setStateName(entity.getStateName());
        dto.setStateType(entity.getStateType());

        dto.setCountryId(
                entity.getCountry() != null ? entity.getCountry().getCountryId() : null
        );

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdateDateTime(entity.getUpdateDateTime());

        return dto;
    }

    public static State toEntity(StateDto dto) {
        if (dto == null) return null;

        State entity = new State();
        entity.setStateId(dto.getStateId());
        entity.setStateCode(dto.getStateCode());
        entity.setStateName(dto.getStateName());
        entity.setStateType(dto.getStateType());

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
