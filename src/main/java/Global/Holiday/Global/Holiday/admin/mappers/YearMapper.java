package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.YearDto;
import Global.Holiday.Global.Holiday.admin.entities.Year;

public class YearMapper {

    public static YearDto toDTO(Year entity) {
        return YearDto.builder()
                .yearId(entity.getYearId())
                .year(entity.getYear())
                .createdBy(entity.getCreatedBy())
                .build();
    }

    public static Year toEntity(YearDto dto) {
        return Year.builder()
                .yearId(dto.getYearId())
                .year(dto.getYear())
                .createdBy(dto.getCreatedBy())
                .build();
    }
}
