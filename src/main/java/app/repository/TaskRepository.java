package app.repository;

import app.dto.TaskCreatedDTO;
import app.exception.TaskNotFound;
import app.model.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class TaskRepository {

    private ArrayList<Task> tasks;

    public TaskRepository() {
        this.tasks = new ArrayList<>();
    }

    public void create(Task task) {

        tasks.add(task);

    }

    public ArrayList<Task> findAll() {

        if (tasks.isEmpty()) {

            throw new TaskNotFound();

        }
        return tasks;
    }

    public List<Task> findLimited(int quantity) {

        if (tasks.isEmpty()) {

            throw new TaskNotFound();

        }

        List<Task> taskList = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {

            taskList.add(tasks.get(i));

        }

        return taskList;

    }

    public Task findById(Long id) {

        return tasks.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id));

    }

    public Task findByName(String name) {

        return tasks.stream()
                .filter(v -> v.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(name));
    }

    public List<Task> findbyDueDateSorted(LocalDate dueDate) {

        return tasks.stream()
                .filter(task -> task.getDueDate().isAfter(dueDate))
                .sorted(Comparator.comparing(Task::getCreatedAt)
                        .thenComparing(Task::getName))
                .toList();

    }

    public Integer findSize() {

        return tasks.size();

    }

    public String findTaskName(Task task) {

        return task.getName();

    }

    public List<Task> findByImportanceLevelSorted(byte level) {

        return tasks.stream()
                .filter(task -> task.getImportanceLevel() == level)
                .sorted(Comparator.comparing(Task::getDueDate)
                        .thenComparing(Task::getCreatedAt))
                .toList();

    }

    public Optional<Task> update(Task task) {

        return Optional.of(
                tasks.stream()
                        .filter(t -> t.getId().equals(task.getId()))
                        .findFirst()
                        .orElseThrow(() -> new TaskNotFound("Задача не найдена"))
        ).map(existingTask -> {

            existingTask.setName(task.getName());
            existingTask.setBody(task.getBody());
            existingTask.setImportanceLevel(task.getImportanceLevel());
            existingTask.setStage(task.getStage());
            existingTask.setDueDate(task.getDueDate());
            return existingTask;

        });
    }

    public void removeById(Long id) {

        tasks.remove(tasks.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TaskNotFound(id)));

    }

    public void removeAll() {

        tasks.clear();

    }

}
