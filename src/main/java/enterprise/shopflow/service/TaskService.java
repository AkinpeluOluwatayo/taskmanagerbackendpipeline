package main.java.enterprise.shopflow.service;

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

    // Logic to save a task with a business rule
    public Task saveTask(Task task) {
        // Business Rule: If status is empty, default it to PENDING
        if (task.getStatus() == null || task.getStatus().isEmpty()) {
            task.setStatus("PENDING");
        }
        return taskRepository.save(task);
    }

    // Logic to find a specific task
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}