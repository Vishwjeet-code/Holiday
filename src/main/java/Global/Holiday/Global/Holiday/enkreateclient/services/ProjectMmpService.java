package Global.Holiday.Global.Holiday.enkreateclient.services;

import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectTask;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProjectMmpService {
    void save(MultipartFile file);

    List<ProjectTask> findAll();
}
