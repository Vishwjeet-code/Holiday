package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.ContinentDto;
import Global.Holiday.Global.Holiday.admin.entities.Continent;

public class ContinentMapper {

    public static ContinentDto toDTO(Continent entity) {
        if (entity == null) return null;

        ContinentDto dto = new ContinentDto();
        dto.setContinentId(entity.getContinentId());
        dto.setContinentCode(entity.getContinentCode());
        dto.setContinentName(entity.getContinentName());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdateDateTime(entity.getUpdateDateTime());

        return dto;
    }

    public static Continent toEntity(ContinentDto dto) {
        if (dto == null) return null;

        Continent entity = new Continent();
        entity.setContinentId(dto.getContinentId());
        entity.setContinentCode(dto.getContinentCode());
        entity.setContinentName(dto.getContinentName());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreateDateTime(dto.getCreateDateTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdateDateTime(dto.getUpdateDateTime());

        return entity;
    }
}
