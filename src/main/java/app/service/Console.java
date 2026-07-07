package app.service;

import app.constants.DataConstants;
import app.dto.TaskCreatedRequest;
import app.dto.TaskDeletedDTO;
import app.dto.TaskShowDTO;
import app.format.BaseFormat;
import app.format.DeleteFormat;
import app.model.Task;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Console {

    private TaskService taskService = new TaskService();

    private JsonManager jsonManager = new JsonManager();

    public String consoleCreateTask(String name, String body, byte importanceLevel, String stage, LocalDate dueDate) {

        TaskCreatedRequest taskCreatedRequest = TaskCreatedRequest.builder()
                .name(name)
                .body(body)
                .importanceLevel(importanceLevel)
                .stage(stage)
                .dueDate(dueDate)
                .priority(importanceLevel)
                .build();

        return BaseFormat.formatCreateTask(taskService.createTask(taskCreatedRequest));

    }

    public String consoleShow() {

        return BaseFormat.formatShowTask(taskService.getAllTasks());

    }

    public String consoleShow(Integer limit) {

        return BaseFormat.formatShowTask(taskService.getLimitedTasks(limit));

    }

    //Сделать конфигурационный класс, где будут находиться поля по умолчанию с их заменой
    public String consoleShow(String field) {

        return "";

    }

    public String consoleShow(Integer limit, String field) {

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

    public void consoleShowImportanceTask() {

        consoleShowImportanceTask(DataConstants.DEFAULT_IMPORTANCE_LEVEL_TASKS);

    }

    public String consoleShowImportanceTask(byte importanceLevel) {

            List<Task> tasks = taskService.getTaskImportanceLevelSorted(importanceLevel);
            List<TaskShowDTO> showDTOs = new ArrayList<>();

            for (Task task : tasks) {

                showDTOs.add(TaskShowDTO.from(task));

            }

            return BaseFormat.formatShowTask(showDTOs);
    }

    public Integer consoleGetSizeTasks() {

        return taskService.getAllTasks().size();

    }

    public TaskShowDTO getTaskById(Long id) {

        return taskService.getTaskById(id);

    }

    public String consoleDeleteAll() {

        taskService.deleteAllTasks();
        return "All tasks deleted successfully";

    }

    public String consoleDeleteById(Long id) {

        taskService.deleteTaskById(id);
        return "Task with ID " + id + " deleted successfully";

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

        return DeleteFormat.taskFormatToOne(task, this);

    }

    public String rootConsoleDeleteTask() {

        return "";

    }

    public String rootConsoleDeleteTask(Long id) {

        return "";

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

    public boolean checkingAvailability(Long id) throws FileNotFoundException {

        return jsonManager.checkingAvailability(id);

    }

    public void deleteLineJson(Long id) {

        jsonManager.deleteLineJson(this, id);

    }

    public JsonManager getJsonManager() {

        return jsonManager;

    }
}
