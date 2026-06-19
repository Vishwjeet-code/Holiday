package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.MonthDto;
import Global.Holiday.Global.Holiday.admin.entities.Month;

public class MonthMapper {

    public static MonthDto toDTO(Month entity) {
        return MonthDto.builder()
                .monthId(entity.getMonthId())
                .month(entity.getMonth())
                .monthName(entity.getMonthName())
                .shortMonthName(entity.getShortMonthName())
                .build();
    }

    public static Month toEntity(MonthDto dto) {
        return Month.builder()
                .monthId(dto.getMonthId())
                .month(dto.getMonth())
                .monthName(dto.getMonthName())
                .shortMonthName(dto.getShortMonthName())
                .build();
    }
}
