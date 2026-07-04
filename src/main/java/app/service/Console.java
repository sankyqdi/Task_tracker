package app.service;

import app.constants.DataDefaultNumber;
import app.exception.*;
import app.format.BaseFormat;
import app.format.DeleteFormat;
import app.model.Task;
import app.param.Param;
import app.repository.TaskRepository;
import lombok.Getter;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Console {

    @Getter
    private final JsonManager jsonManager = new JsonManager();

    private final TaskRepository taskRepository = new TaskRepository();

    public String consoleAdd(@Param("name") String name, @Param("body") String body,
                             @Param("importanceLevel") String importanceLevel, @Param("dueDate") String dueDate) {

        if (name.isEmpty() || body == null) {

            throw new IncorrectDataEntry("Incorrect data. \" +\n" +
                    "\"Please review the task completion rules again.");

        }

        try {
            Task task = new Task();
            task.setName(name);
            task.setBody(body);
            task.setStage("Create task");

            task.setDueDate(LocalDate.parse(dueDate));

            if (Byte.parseByte(importanceLevel) == 0){

                task.setImportanceLevel((byte) 1);

            } else {

                task.setImportanceLevel(Byte.parseByte(importanceLevel));

            }

            taskRepository.create(task);

            return BaseFormat.formatCreateTask(task);
        } catch (DateTimeParseException e) {

            throw new DateError("The date is in the wrong format",  e);

        }



    }

    public void consoleAdd(String name, String body, String importanceLevel,
                             LocalDate dueDate, String stage, LocalDate createdAt, Long id) {

        if (name.isEmpty()
                ||
                body == null
                ||
                dueDate == null
                ||
                stage.isEmpty()
                ||
                createdAt == null
                ||
                id == null
        ) {

            throw new TaskNotCreated(new IncorrectDataEntry("Invalid data in file"));

        }

        try {
            Task task = new Task();
            task.setName(name);
            task.setBody(body);

            task.setDueDate(dueDate);
            task.setCreatedAt(createdAt);
            task.setStage(stage);
            task.setId(id);

            if (Byte.parseByte(importanceLevel) == 0){

                task.setImportanceLevel((byte) 1);

            } else {

                task.setImportanceLevel(Byte.parseByte(importanceLevel));

            }

            taskRepository.create(task);
        } catch (DateTimeParseException e) {

            throw new TaskNotCreated(new IncorrectDataEntry("The date from the file is in the wrong format."));

        }



    }

    public List<Task> consoleGetAllTasks() {

        return taskRepository.show();

    }

    public String consoleShow() {

        return consoleShow(DataDefaultNumber.DEFAULT_NUMBER_SIZE_TASKS);

    }

    public String consoleShow(Integer sizeTasks) {



        if (sizeTasks == null || sizeTasks <= 0) {

            sizeTasks = DataDefaultNumber.DEFAULT_NUMBER_SIZE_TASKS;

        } else if (sizeTasks > taskRepository.showSizeTask()) {

            sizeTasks = taskRepository.showSizeTask();

        }

        var tasks = taskRepository.getTasks(sizeTasks);

        return BaseFormat.formatShowTask(tasks);


    }

    public String consoleShowById(Long id) {
        try {

            long longId = id;
            if (id > taskRepository.showSizeTask()) {

                longId = taskRepository.showSizeTask();

            }

            List<Task> tasks = new ArrayList<>();
            tasks.add(taskRepository.getTaskById(longId));
            return BaseFormat.formatShowTask(tasks);

        } catch (TaskNotFound e) {

            throw new TaskNotFound(id);

        }

    }

    public  String consoleShowByName(String name) {

        List<Task> tasks = new ArrayList<>();
        tasks.add(taskRepository.getTaskByName(name));
        return BaseFormat.formatShowTask(tasks);


    }

    public String consoleShowImportanceTask() {

        byte importanceLevel = DataDefaultNumber.DEFAULT_IMPORTANCE_LEVEL_TASKS;
        return consoleShowImportanceTask(String.valueOf(importanceLevel));

    }

    public String consoleShowImportanceTask(String fixedImportanceLevel) {
        String taskString = "Найденные задачи с уровнем важности " + fixedImportanceLevel + ":\n";
        var tasks = taskRepository.showImportant(Byte.parseByte(fixedImportanceLevel));

        if (tasks == null || tasks.isEmpty()) {

            return "You don't have any important tasks.";

        } else {

            return taskString + BaseFormat.formatShowTask(tasks);

        }

    }

    public String consoleShowStageById(Long id) {

        if (id == null) {

            throw new IncorrectDataEntry("Empty id input");

        }

        var task = taskRepository.getTaskById(id);

        return task.getStage();

    }

    public String consoleShowNameById(Long id) {

        if (id == null) {

            throw new IncorrectDataEntry("Empty id input");

        }

        var task = taskRepository.getTaskById(id);

        return  task.getName();

    }

    public  LocalDate consoleShowDueDateById(Long id) {

        if (id == null) {

            throw new IncorrectDataEntry("Empty id input");

        }

        var task = taskRepository.getTaskById(id);

        return task.getDueDate();

    }

    public  String consoleSetStage(Long id, String stage) {

        if (id == null || id <= 0 || stage == null) {

            throw new TaskNotUpdated("stage", new IncorrectDataEntry("Invalid data. Stage - '" + stage
                    +
                    "', identifier - '" + id + "'"));

        }

        var task = taskRepository.getTaskById(id);
        return taskRepository.completedMark(task, stage).toString();

    }

    public String consoleSetDueDate(Long id, String dueDate) {

        if (id == null || id <= 0 || dueDate == null) {

            throw new TaskNotUpdated("Completion date",
                    new IncorrectDataEntry("Invalid data. "
                    +
                    "Completion date - '"
                    +
                    dueDate
                    +
                    "', identifier - '" + id + "'"));

        }

        var task = taskRepository.getTaskById(id);
        return taskRepository.setDateComplate(task, LocalDate.parse(dueDate)).toString();

    }

    public String consoleDeleteById(Long id) throws FileNotFoundException {

        var task = taskRepository.getTaskById(id);
            taskRepository.delete(id);
            return DeleteFormat.taskFormatToOne(task, jsonManager)
                    +
                    "\n"
                    +
                    "The task has been successfully deleted!\n" +
                    "+\n" +
                    "\"Uncompleted tasks remain:" + taskRepository.showSizeTask();

    }

    public String consoleDeleteAll() {
        String stringDeleteTasks = DeleteFormat.taskFormat(getAllNameTasks());
        taskRepository.deleteAll();
        return  stringDeleteTasks;
    }

    public Integer getSizeTasks() {

        return taskRepository.showSizeTask();

    }

    public Task getTaskById(Long id) {

        return taskRepository.getTaskById(id);

    }

    public List<String> getAllNameTasks() {

        List<String> nameTasks = new ArrayList<>();
        var tasks = taskRepository.show();

        for (var task : tasks) {

            nameTasks.add(taskRepository.getTaskName(task));

        }

        return nameTasks;

    }

}
