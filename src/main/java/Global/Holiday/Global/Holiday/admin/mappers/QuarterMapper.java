package Global.Holiday.Global.Holiday.admin.mappers;

import Global.Holiday.Global.Holiday.admin.dtos.QuarterDto;
import Global.Holiday.Global.Holiday.admin.entities.Quarter;

public class QuarterMapper {

    public static QuarterDto toDTO(Quarter entity) {
        return QuarterDto.builder()
                .quarterId(entity.getQuarterId())
                .quarter(entity.getQuarter())
                .quarterCode(entity.getQuarterCode())
                .build();
    }

    public static Quarter toEntity(QuarterDto dto) {
        return Quarter.builder()
                .quarterId(dto.getQuarterId())
                .quarter(dto.getQuarter())
                .quarterCode(dto.getQuarterCode())
                .build();
    }
}
