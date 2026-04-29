package enterprise.shopflow.service;

import enterprise.shopflow.model.Task;
import enterprise.shopflow.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task testTask;

    @BeforeEach
    void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setTitle("Test Task");
        testTask.setDescription("Test Description");
        testTask.setStatus("TODO");
    }

    @Test
    void getAllTasks_ShouldReturnListOfTasks() {

        List<Task> tasks = Arrays.asList(testTask);
        when(taskRepository.findAll()).thenReturn(tasks);


        List<Task> result = taskService.getAllTasks();


        assertEquals(1, result.size());
        assertEquals("Test Task", result.get(0).getTitle());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void getAllTasks_WhenEmpty_ShouldReturnEmptyList() {

        when(taskRepository.findAll()).thenReturn(Arrays.asList());


        List<Task> result = taskService.getAllTasks();


        assertTrue(result.isEmpty());
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void saveTask_WithStatus_ShouldKeepExistingStatus() {

        testTask.setStatus("IN_PROGRESS");
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);


        Task result = taskService.saveTask(testTask);


        assertEquals("IN_PROGRESS", result.getStatus());
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void saveTask_WithNullStatus_ShouldDefaultToPending() {

        testTask.setStatus(null);
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Task result = taskService.saveTask(testTask);


        assertEquals("PENDING", result.getStatus());
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void saveTask_WithEmptyStatus_ShouldDefaultToPending() {

        testTask.setStatus("");
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Task result = taskService.saveTask(testTask);


        assertEquals("PENDING", result.getStatus());
        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    void getTaskById_WhenExists_ShouldReturnTask() {

        when(taskRepository.findById(1L)).thenReturn(Optional.of(testTask));


        Optional<Task> result = taskService.getTaskById(1L);


        assertTrue(result.isPresent());
        assertEquals("Test Task", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void getTaskById_WhenNotExists_ShouldReturnEmpty() {

        when(taskRepository.findById(2L)).thenReturn(Optional.empty());


        Optional<Task> result = taskService.getTaskById(2L);

        assertFalse(result.isPresent());
        verify(taskRepository, times(1)).findById(2L);
    }

    @Test
    void deleteTask_ShouldCallRepositoryDelete() {

        taskService.deleteTask(1L);


        verify(taskRepository, times(1)).deleteById(1L);
    }
}
