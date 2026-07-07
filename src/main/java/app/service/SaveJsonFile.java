package app.service;

import app.exception.JsonError;
import app.model.Task;
import app.util.LogUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;


public class SaveJsonFile {

    private static final Logger log = LogUtil.getLogger(SaveJsonFile.class);

    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    private SaveJsonFile(){}

    public static void saveAllTasks(List<Task> tasks, Path filePath) throws IOException {

        File file = filePath.toFile();
        File backup = new File(file.getParent(), "Tasks_backup.jsonl");

        if (file.exists()) {
            Files.copy(file.toPath(), backup.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }


        try (FileWriter writer = new FileWriter(file, StandardCharsets.UTF_8)) {

            for (Task task : tasks) {

                writer.write(mapper.writeValueAsString(task));
                writer.write("\n");

            }


        } catch (IOException e) {

            if (backup.exists()) {
                try {

                    Files.copy(backup.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    log.error("Ошибка записи -> {}", e.getMessage());
                    throw new JsonError("Ошибка записи данных файл. Данные восстановлены из резервной копии", e);


                } catch (IOException copyError) {

                    throw new JsonError("Ошибка восстановления из временного файла;  ", backup.toPath(), copyError, e);

                }

            }

            throw new JsonError("Ошибка записи файла. Резервная копия не создана: ", e);

        }

            try {

                Files.deleteIfExists(backup.toPath());

            } catch (IOException e) {

                log.error("Не получилось удалить временный файл {}. Ошибка -> {}", backup.toPath(), e.getMessage());

            }

    }

    public static List<Task> readJsonFiles(File file) throws IOException {

        List<Task> tasks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null ) {

                if (!line.trim().isEmpty()) {

                    Task task = mapper.readValue(line, Task.class);
                    tasks.add(task);

                }
            }
        }

        return tasks;
    }

    public static void rewriteJsonFile(File file) throws IOException {

        try (FileWriter writer = new FileWriter(file)) {
            // Файл будет очищен при закрытии FileWriter
        } catch (IOException e) {

            throw new IOException("Ошибка при очистке файла задач", e);

        }

    }

    public static String deleteLineById(Path filePath, Long id) throws IOException {

       File file = filePath.toFile();

        if (!file.exists()) {

            throw new FileNotFoundException("File not found: " + filePath);

        }

        File backup = new File(file.getParent(), "Tasks_backup_before_delete.json");
        Files.copy(filePath, backup.toPath(), StandardCopyOption.REPLACE_EXISTING);

        try {

            List<String> list = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            List<String> updateList = new ArrayList<>();
            boolean check = false;

            for (String line : list) {

                if (line.trim().isEmpty()) {

                    continue;

                }
                try {
                    JsonNode node = mapper.readTree(line);

                    if (node.has("id") && node.get("id").asLong() == id) {

                        check = true;
                        continue;

                    }

                } catch (IOException e) {

                    return "Error read line";

                }

                updateList.add(line);

            }

            if (check) {

                Files.write(filePath, updateList, StandardCharsets.UTF_8);
                Files.deleteIfExists(backup.toPath());
                return "✅ Задача успешно удалена из хранилища.";

            } else {

                return "❌ Задача с ID " + id + " не найдена в хранилище.";

            }

        } catch (IOException e) {


            if(backup.exists()) {

                Files.copy(backup.toPath(), filePath, StandardCopyOption.REPLACE_EXISTING);
                return "An error occurred while modifying the file.";

            }

            throw e;

        }




    }


    public static boolean searchTaskById(File file, String id) throws FileNotFoundException {

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = reader.readLine()) != null && !line.trim().isEmpty()) {

                JsonNode idLine = mapper.readTree(line).get("id");

                if (id.equals(idLine.asText())) {

                    return true;

                }

            }

            return false;


        } catch(IOException e) {

            throw new FileNotFoundException(e.getMessage());

        }

    }

    public static boolean containsValidJson(File file) throws IOException {
        if (file == null || !file.exists() || file.length() == 0) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .anyMatch(SaveJsonFile::isValidJson);
        }
    }

    public static boolean isValidJson(String line) {
        if (line == null || line.trim().isEmpty()) {
            return false;
        }

        try {
            mapper.readTree(line);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}


