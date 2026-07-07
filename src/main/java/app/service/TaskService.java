package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskCreatedRequest;
import app.dto.TaskShowDTO;
import app.model.Task;
import app.repository.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class TaskService {

    private  final TaskRepository taskRepository = new TaskRepository();

    public TaskService(){}

    public TaskCreatedDTO createTask(TaskCreatedRequest taskDTO) {

        Task task = new Task(
                taskDTO.getName(),
                taskDTO.getBody(),
                taskDTO.getImportanceLevel(),
                taskDTO.getStage(),
                taskDTO.getDueDate()
        );

        if (taskDTO.getPriority() > 0) {
            task.setPriority(taskDTO.getPriority());
        }

        if (taskDTO.getBuiltInTags() != null && !taskDTO.getBuiltInTags().isEmpty()) {
            for (String tagName : taskDTO.getBuiltInTags()) {
                try {
                    task.addBuiltInTag(Enum.valueOf(app.model.TaskTag.class, tagName.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    // Skip invalid tag names
                }
            }
        }

        if (taskDTO.getCustomTags() != null && !taskDTO.getCustomTags().isEmpty()) {
            for (String customTag : taskDTO.getCustomTags()) {
                task.addCustomTag(customTag);
            }
        }

        taskRepository.create(task);

        return TaskCreatedDTO.from(task);

    }

    public void createTask(Task task) {

        taskRepository.create(task);

    }

    public List<TaskShowDTO> getAllTasks() {

        return taskRepository.findAll().stream()
                .map(TaskShowDTO::from)
                .toList();

    }

    public List<Task> getAllTasks(String nullName) {

        return taskRepository.findAll();

    }

    public List<TaskShowDTO> getLimitedTasks(Integer size) {

        if(size > taskRepository.findSize()) {

            return taskRepository.findLimited(taskRepository.findSize()).stream()
                    .map(TaskShowDTO::from)
                    .toList();

        }

        return taskRepository.findLimited(size).stream()
                .map(TaskShowDTO::from)
                .toList();

    }

    public TaskShowDTO getTaskById(Long id) {

        return TaskShowDTO.from(taskRepository.findById(id));

    }

    public TaskShowDTO getTaskByName(String name) {

        return TaskShowDTO.from(taskRepository.findByName(name));

    }

    public List<Task> getTaskImportanceLevelSorted(byte level) {

        return taskRepository.findByImportanceLevelSorted(level);

    }

    public List<Task> getTaskDueDateSorted(LocalDate dueDate) {

        return taskRepository.findbyDueDateSorted(dueDate);

    }

    public List<String> getAllTaskName() {

        return taskRepository.findAll().stream()
                .map(Task::getName)
                .toList();

    }

    public void updateTaskStage(Long id, String stage) {

        Task task = taskRepository.findById(id);
        task.setStage(stage);
        task.setUpdatedAt(LocalDate.now());
        taskRepository.update(task);

    }

    public void updateTaskDueDate(Long id, LocalDate dueDate) {

        Task task = taskRepository.findById(id);
        task.setDueDate(dueDate);
        task.setUpdatedAt(LocalDate.now());
        taskRepository.update(task);

    }

    public void updateTaskImportanceLevel(Long id, byte level) {

        Task task = taskRepository.findById(id);
        task.setImportanceLevel(level);
        task.setUpdatedAt(LocalDate.now());
        taskRepository.update(task);

    }

    public void updateTaskPriority(Long id, byte priority) {

        Task task = taskRepository.findById(id);
        task.setPriority(priority);
        task.setUpdatedAt(LocalDate.now());
        taskRepository.update(task);

    }

    public void updateTaskCompleted(Long id, boolean isCompleted) {

        Task task = taskRepository.findById(id);
        task.setCompleted(isCompleted);
        task.setUpdatedAt(LocalDate.now());
        taskRepository.update(task);

    }

    public void deleteAllTasks() {

        taskRepository.removeAll();

    }

    public void deleteTaskById(Long id) {

        taskRepository.removeById(id);

    }

}
