package app.service;

import app.exception.IncorrectDataEntry;
import app.exception.JsonError;
import app.model.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class JsonManager {

    private final Path JSON_FILE_PATH;

    public JsonManager() {

        this(Path.of("src/main/resources/json/Tasks.jsonl"));

    }

    public JsonManager(Path jsonFilePath) {

        this.JSON_FILE_PATH = jsonFilePath;

    }

    public  void startWriterTasks (Console console) {

        try {

            File file = JSON_FILE_PATH.toFile();
            var tasks = SaveJsonFile.readJsonFiles(file);

            long id = tasks.stream()
                    .mapToLong(Task::getId)
                    .max()
                    .orElse(0);

            Task.setIdGenerator(id);

            for (var task : tasks) {

                console.consoleAdd(task.getName(), task.getBody(), String.valueOf(task.getImportanceLevel()),
                        task.getDueDate(), task.getStage(), task.getCreatedAt(), task.getId());

            }

            if(tasks.isEmpty()) {

                throw new JsonError("The launch was successful, but no tasks were found yet.");

            }

        } catch (IOException e) {

            throw new JsonError(JSON_FILE_PATH, e);

        }

    }

    public  void endWriterTasks(Console console) {
        try {

            List<Task> tasks = console.consoleGetAllTasks();
            SaveJsonFile.saveAllTasks(tasks, JSON_FILE_PATH);

        } catch (IOException e) {

            throw new RuntimeException("Failed to save tasks", e);

        }
    }

    public  void turningEmptyFile() {

        try {

            SaveJsonFile.rewriteJsonFile(JSON_FILE_PATH.toFile());

        } catch (IOException e) {

            throw new RuntimeException("An unexpected error occurred while deleting the contents of the file.");

        }

    }

    public void deleteLineJson(Console console, Long id) throws IOException {

        String operationOutput = SaveJsonFile.deleteLineById(JSON_FILE_PATH, id);


    }


    public boolean checkingAvailability(Long id) throws FileNotFoundException {

        if(id == null) {

            throw new IncorrectDataEntry("Invalid task entry");

        }

        String idStr = String.valueOf(id);

        return SaveJsonFile.searchTaskById(JSON_FILE_PATH.toFile(), idStr.trim());

    }

}
