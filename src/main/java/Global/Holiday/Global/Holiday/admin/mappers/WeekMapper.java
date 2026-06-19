package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.WeekDto;
import Global.Holiday.Global.Holiday.admin.entities.Week;

public class WeekMapper {

    public static WeekDto toDTO(Week entity) {
        return WeekDto.builder()
                .weekId(entity.getWeekId())
                .week(entity.getWeek())
                .weekName(entity.getWeekName())
                .weekCode(entity.getWeekCode())
                .build();
    }

    public static Week toEntity(WeekDto dto) {
        return Week.builder()
                .weekId(dto.getWeekId())
                .week(dto.getWeek())
                .weekName(dto.getWeekName())
                .weekCode(dto.getWeekCode())
                .build();
    }
}

