package enterprise.shopflow.service;

import enterprise.shopflow.model.Task;
import enterprise.shopflow.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // Logic to get all tasks
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    public Task saveTask(Task task) {

        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("PENDING");
        }
        return taskRepository.save(task);
    }


    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}