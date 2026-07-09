package app.service;

import app.dto.TaskCreatedDTO;
import app.dto.TaskCreatedRequest;
import app.dto.TaskShowDTO;
import app.model.Task;
import app.model.TaskTag;
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

            for (String tagName : taskDTO.getBuiltInTags()) {
                try {
                    task.addBuiltInTag(Enum.valueOf(app.model.TaskTag.class, tagName.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    // Skip invalid tag names
                }
            }

        if (!taskDTO.getCustomTags().isEmpty()) {
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

    public List<TaskShowDTO> getTaskImportanceLevelSorted(byte level) {
        return taskRepository.findByImportanceLevelSorted(level).stream()
                .map(TaskShowDTO::from)
                .toList();
    }

    public List<TaskShowDTO> getTaskImportanceLevelSorted(byte level, int limit) {
        return taskRepository.findByImportanceLevelSorted(level).stream()
                .map(TaskShowDTO::from)
                .limit(limit)
                .toList();
    }

    public List<TaskShowDTO> getTaskDueDateSorted(LocalDate dueDate) {
        return taskRepository.findbyDueDateSorted(dueDate).stream()
                .map(TaskShowDTO::from)
                .toList();
    }

    public List<TaskShowDTO> getTaskDueDateSorted(LocalDate dueDate, int limit) {
        return taskRepository.findbyDueDateSorted(dueDate).stream()
                .map(TaskShowDTO::from)
                .limit(limit)
                .toList();
    }

    public List<TaskShowDTO> getTaskStageSorted(String stage) {
        return taskRepository.findStageSorted(stage).stream()
                .map(TaskShowDTO::from)
                .toList();
    }

    public List<TaskShowDTO> getTaskStageSorted(String stage, int limit) {
        return taskRepository.findStageSorted(stage).stream()
                .map(TaskShowDTO::from)
                .limit(limit)
                .toList();
    }

    public List<TaskShowDTO> getTaskTagSorted(TaskTag taskTag) {
        return taskRepository.findTagsSorted(taskTag).stream()
                .map(TaskShowDTO::from)
                .toList();
    }

    public List<TaskShowDTO> getTaskTagSorted(TaskTag taskTag, int limit) {
        return taskRepository.findTagsSorted(taskTag).stream()
                .map(TaskShowDTO::from)
                .limit(limit)
                .toList();
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
