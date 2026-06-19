package Global.Holiday.Global.Holiday.enkreateclient.mapper;

import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.ProjectDetailsDto;
import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectDetails;

public class ProjectDetailsMapper {


    public static ProjectDetailsDto toDto(ProjectDetails Entity){

        if(Entity==null){
            return null;
        }
        ProjectDetailsDto dto = new ProjectDetailsDto();
        dto.setProjectDetailsId(Entity.getProjectDetailsId());
        dto.setProjectCityId(Entity.getProjectCityId());
        dto.setProjectName(Entity.getProjectName());


        return dto;
    }

    public static ProjectDetails toEntity(ProjectDetailsDto dto) {

        if (dto == null) {
            return null;
        }

        ProjectDetails entity = new ProjectDetails();

        entity.setProjectDetailsId(dto.getProjectDetailsId());
        entity.setProjectName(dto.getProjectName());
        entity.setProjectCityId(dto.getProjectCityId());
        entity.setProjectCityPinId(dto.getProjectCityPinId());

        return entity;
    }
}
