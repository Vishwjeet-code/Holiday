package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.IndustryDto;
import Global.Holiday.Global.Holiday.admin.entities.Industry;

public class IndustryMapper {

    public static IndustryDto toDTO(Industry entity) {
        if (entity == null) return null;

        IndustryDto dto = new IndustryDto();
        dto.setIndustryId(entity.getIndustryId());
        dto.setIndustryCode(entity.getIndustryCode());
        dto.setIndustryType(entity.getIndustryType());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreatedTime(entity.getCreatedTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdatedTime(entity.getUpdatedTime());

        return dto;
    }

    public static Industry toEntity(IndustryDto dto) {
        if (dto == null) return null;

        Industry entity = new Industry();
        entity.setIndustryId(dto.getIndustryId());
        entity.setIndustryCode(dto.getIndustryCode());
        entity.setIndustryType(dto.getIndustryType());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreatedTime(dto.getCreatedTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdatedTime(dto.getUpdatedTime());

        return entity;
    }
}
