package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.DayDto;
import Global.Holiday.Global.Holiday.admin.entities.Day;

public class DayMapper {

    public static DayDto toDTO(Day entity) {
        return DayDto.builder()
                .dayId(entity.getDayId())
                .day(entity.getDay())
                .build();
    }

    public static Day toEntity(DayDto dto) {
        return Day.builder()
                .dayId(dto.getDayId())
                .day(dto.getDay())
                .build();
    }
}
