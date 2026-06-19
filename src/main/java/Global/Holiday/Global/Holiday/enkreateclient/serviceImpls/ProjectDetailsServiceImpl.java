package Global.Holiday.Global.Holiday.enkreateclient.serviceImpls;

import Global.Holiday.Global.Holiday.enkreateclient.globalholidaydto.ProjectDetailsDto;
import Global.Holiday.Global.Holiday.enkreateclient.mapper.ProjectDetailsMapper;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.ProjectDetailsRepository;
import Global.Holiday.Global.Holiday.enkreateclient.services.ProjectDetailService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProjectDetailsServiceImpl implements ProjectDetailService {


    private final ProjectDetailsRepository projectDetailsRepository;

    public ProjectDetailsServiceImpl(ProjectDetailsRepository projectDetailsRepository) {
        this.projectDetailsRepository = projectDetailsRepository;
    }

    @Override
    public List<ProjectDetailsDto> getAllProjects() {
        return projectDetailsRepository.findAll().stream().map(ProjectDetailsMapper::toDto).toList();
    }
}
