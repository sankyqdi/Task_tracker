package app.service;

import app.config.ConfigurationManager;
import app.dto.TaskCreatedRequest;
import app.dto.TaskDeletedDTO;
import app.dto.TaskShowDTO;
import app.exception.RootAccessDeniedException;
import app.format.BaseFormat;
import app.format.DeleteFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

public class Console {

    private final TaskService taskService = new TaskService();

    @Getter
    private final JsonManager jsonManager = new JsonManager();

    private final ConfigurationManager instance = ConfigurationManager.getInstance();

    public String consoleCreateTask(String name, String body, byte importanceLevel, String stage,
                                    LocalDate dueDate, List<String> taskTag, List<String> customTag) {

        TaskCreatedRequest taskCreatedRequest = TaskCreatedRequest.builder()
                .name(name)
                .body(body)
                .importanceLevel(importanceLevel)
                .stage(stage)
                .dueDate(dueDate)
                .builtInTags(taskTag)
                .customTags(customTag)
                .build();

        return BaseFormat.formatCreateTask(taskService.createTask(taskCreatedRequest));

    }

    public String consoleShow() {

        return BaseFormat.formatShowTask(taskService.getAllTasks());

    }

    public String consoleShow(Integer limit) {

        return BaseFormat.formatShowTask(taskService.getLimitedTasks(limit));

    }

    public String consoleShow(String field) {

        if (consoleGetSizeTasks() == 0) {

            return "Задач в памяти не найдено";

        }

        switch (field) {

            case "stage" -> BaseFormat.formatShowTask(taskService.getTaskStageSorted(instance.getStage()));
            case "dueDate" ->  BaseFormat.formatShowTask(taskService.getTaskDueDateSorted(instance.getDueDate()));
            case "tag" -> BaseFormat.formatShowTask(taskService.getTaskTagSorted(instance.getTaskTag()));
            case "level" -> BaseFormat.formatShowTask(taskService.getTaskImportanceLevelSorted(instance.getImplementsLevel()));

        }

        return "";

    }

    public String consoleShow(int limit, String field) {

        if (consoleGetSizeTasks() == 0) {

            return "Задач в памяти не найдено";

        }

        switch (field) {

            case "stage" -> BaseFormat.formatShowTask(taskService.getTaskStageSorted(instance.getStage(), limit));
            case "dueDate" ->  BaseFormat.formatShowTask(taskService.getTaskDueDateSorted(instance.getDueDate(), limit));
            case "tag" -> BaseFormat.formatShowTask(taskService.getTaskTagSorted(instance.getTaskTag(), limit));
            case "level" -> BaseFormat.formatShowTask(taskService.getTaskImportanceLevelSorted(instance.getImplementsLevel(), limit));

        }

        return "";

    }

    public String consoleShowOne(Long id) {

        List<TaskShowDTO> tasks = new ArrayList<>();
        tasks.add(taskService.getTaskById(id));
        return BaseFormat.formatShowTask(tasks);

    }

    public String consoleShowOne(String name) {

        List<TaskShowDTO> tasks = new ArrayList<>();
        tasks.add(taskService.getTaskByName(name));
        return BaseFormat.formatShowTask(tasks);

    }

    public String consoleShowNameById(Long id) {

        try {
            TaskShowDTO task = taskService.getTaskById(id);
            return task.getName();
        } catch (Exception e) {
            return "Unknown";
        }

    }

    public String consoleShowStageById(Long id) {

        try {
            TaskShowDTO task = taskService.getTaskById(id);
            return task.getStage();
        } catch (Exception e) {
            return "Unknown";
        }

    }

    public String consoleShowDueDateById(Long id) {

        try {
            TaskShowDTO task = taskService.getTaskById(id);
            return task.getDueDate() != null ? task.getDueDate().toString() : "Not set";
        } catch (Exception e) {
            return "Unknown";
        }

    }

    public void consoleSetStage(Long id, String stage) {

        taskService.updateTaskStage(id, stage);

    }

    public void consoleSetDueDate(Long id, String dueDate) {

        try {
            LocalDate date = LocalDate.parse(dueDate);
            taskService.updateTaskDueDate(id, date);
        } catch (Exception e) {
            System.out.println("Invalid date format");
        }

    }

    public Integer consoleGetSizeTasks() {

        return taskService.getAllTasks().size();

    }

    public TaskShowDTO consoleGetTaskById(Long id) {

        return taskService.getTaskById(id);

    }

    public String consoleUpdateStageTask(Long id, String stage) {

        taskService.updateTaskStage(id, stage);
        TaskShowDTO updatedTask = taskService.getTaskById(id);
        List<TaskShowDTO> tasks = new ArrayList<>();
        tasks.add(updatedTask);
        return BaseFormat.formatShowTask(tasks);

    }

    public String consoleUpdateLevelTask(Long id, byte level) {

        taskService.updateTaskImportanceLevel(id, level);
        TaskShowDTO updatedTask = taskService.getTaskById(id);
        List<TaskShowDTO> tasks = new ArrayList<>();
        tasks.add(updatedTask);
        return BaseFormat.formatShowTask(tasks);

    }

    public String consoleUpdateDueDateTask(Long id, LocalDate dueDate) {

        taskService.updateTaskDueDate(id, dueDate);
        TaskShowDTO updatedTask = taskService.getTaskById(id);
        List<TaskShowDTO> tasks = new ArrayList<>();
        tasks.add(updatedTask);
        return BaseFormat.formatShowTask(tasks);

    }

    public String consoleDeleteTask() {

        var nameTasks = taskService.getAllTaskName();
        taskService.deleteAllTasks();

        return DeleteFormat.taskFormat(nameTasks);

    }

    public String consoleDeleteTask(Long id) {

        TaskShowDTO taskShowDTO = taskService.getTaskById(id);
        TaskDeletedDTO task = TaskDeletedDTO.from(taskShowDTO);
        taskService.deleteTaskById(id);

        return DeleteFormat.taskFormatToOne(task);

    }

    public String rootConsoleDeleteTask() {

        if (!instance.getRoot()) {

            throw new RootAccessDeniedException();

        }

        var nameTask = taskService.getAllTaskName();
        jsonManager.turningEmptyFile();
        consoleDeleteTask();

        return DeleteFormat.rootTaskFormat(nameTask);

    }

    public String rootConsoleDeleteTask(Long id) {

        if (!instance.getRoot()) {

            throw new RootAccessDeniedException();

        }

        TaskDeletedDTO taskDeletedDTO = TaskDeletedDTO.from(taskService.getTaskById(id));
        jsonManager.turningEmptyFile();
        consoleDeleteTask();

        return DeleteFormat.rootTaskFormat(taskDeletedDTO);

    }

    public void consoleStartApp() {

        var tasks = jsonManager.startWriterTasks();

        for(var task : tasks) {

            taskService.createTask(task);

        }
    }

    public void consoleEndApp() {

        jsonManager.endWriterTasks(taskService.getAllTasks(null));

    }

    public boolean checkIsEmpty() {

        return jsonManager.checkIsEmpty();

    }

    public boolean checkingAvailability(Long id)  {

            return jsonManager.checkingAvailability(id);

    }

    public void deleteLineJson(Long id) {

        jsonManager.deleteLineJson(this, id);

    }

}
