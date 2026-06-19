package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.DateDto;
import Global.Holiday.Global.Holiday.admin.entities.*;


public class DateMapper {

    public static DateDto toDTO(date entity) {
        if (entity == null) return null;

        DateDto dto = new DateDto();
        dto.setDateId(entity.getDateId());
        dto.setDate(entity.getDate());

        dto.setDayId(entity.getDay() != null ? entity.getDay().getDayId() : null);
        dto.setWeekId(entity.getWeek() != null ? entity.getWeek().getWeekId() : null);
        dto.setMonthId(entity.getMonth() != null ? entity.getMonth().getMonthId() : null);
        dto.setQuarterId(entity.getQuarter() != null ? entity.getQuarter().getQuarterId() : null);
        dto.setYearId(entity.getYear() != null ? entity.getYear().getYearId() : null);

        dto.setCreatedBy(entity.getCreatedBy());
        dto.setCreateDateTime(entity.getCreateDateTime());
        dto.setUpdatedBy(entity.getUpdatedBy());
        dto.setUpdateDateTime(entity.getUpdateDateTime());

        return dto;
    }

    public static date toEntity(DateDto dto) {
        if (dto == null) return null;

        date entity = new date();
        entity.setDateId(dto.getDateId());
        entity.setDate(dto.getDate());

        // Only setting IDs (you'll fetch actual entities in service layer)
        if (dto.getDayId() != null) {
            Day day = new Day();
            day.setDayId(dto.getDayId());
            entity.setDay(day);
        }

        if (dto.getWeekId() != null) {
            Week week = new Week();
            week.setWeekId(dto.getWeekId());
            entity.setWeek(week);
        }

        if (dto.getMonthId() != null) {
            Month month = new Month();
            month.setMonthId(dto.getMonthId());
            entity.setMonth(month);
        }

        if (dto.getQuarterId() != null) {
            Quarter quarter = new Quarter();
            quarter.setQuarterId(dto.getQuarterId());
            entity.setQuarter(quarter);
        }

        if (dto.getYearId() != null) {
            Year year = new Year();
            year.setYearId(dto.getYearId());
            entity.setYear(year);
        }

        entity.setCreatedBy(dto.getCreatedBy());
        entity.setCreateDateTime(dto.getCreateDateTime());
        entity.setUpdatedBy(dto.getUpdatedBy());
        entity.setUpdateDateTime(dto.getUpdateDateTime());

        return entity;
    }
}