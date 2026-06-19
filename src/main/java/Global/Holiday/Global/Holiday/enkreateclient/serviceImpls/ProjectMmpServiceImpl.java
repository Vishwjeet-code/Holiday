package Global.Holiday.Global.Holiday.enkreateclient.serviceImpls;

import Global.Holiday.Global.Holiday.enkreateclient.entities.ProjectTask;
import Global.Holiday.Global.Holiday.enkreateclient.repositories.ProjectTaskRepository;
import Global.Holiday.Global.Holiday.enkreateclient.services.ProjectMmpService;
import org.mpxj.ProjectFile;
import org.mpxj.Task;
import org.mpxj.reader.UniversalProjectReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public class ProjectMmpServiceImpl implements ProjectMmpService {

    @Autowired
    ProjectTaskRepository projectTaskRepository;

    @Override
    public void save(MultipartFile file) {
        try {

            File tempFile = File.createTempFile("project", ".mpp");
            file.transferTo(tempFile);

            ProjectFile project = new UniversalProjectReader().read(tempFile);

            for (Task task : project.getTasks()) {

                if (task.getName() == null) {
                    continue;
                }

                ProjectTask entity = new ProjectTask();

                if (task.getID() != null) {
                    entity.setMppTaskId(task.getID().longValue());
                }

                entity.setTaskName(task.getName());

                if (task.getDuration() != null) {
                    entity.setDuration(task.getDuration().toString());
                }

                // Start Date
                if (task.getStart() != null) {
                    entity.setStartDate(task.getStart());
                }

                // Finish Date
                if (task.getFinish() != null) {
                    entity.setFinishDate(task.getFinish());
                }

                entity.setOutlineLevel(task.getOutlineLevel());

                if (task.getParentTask() != null &&
                        task.getParentTask().getID() != null) {

                    entity.setParentTaskId(
                            task.getParentTask().getID().longValue()
                    );
                }

                projectTaskRepository.save(entity);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ProjectTask> findAll() {
        return projectTaskRepository.findAll();
    }
}

