package app.dto;

import app.model.Task;
import app.model.TaskTag;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class TaskCreatedDTO {

    private Long id;

    private String name;

    private String body;

    private byte importanceLevel;

    private byte priority;

    private LocalDate dueDate;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private boolean isCompleted;

    private List<String> builtInTags;

    private List<String> customTags;

    private Double estimatedHours;

    public static TaskCreatedDTO from(Task task) {

        return TaskCreatedDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .body(task.getBody())
                .importanceLevel(task.getImportanceLevel())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .isCompleted(task.isCompleted())
                .builtInTags(task.getBuiltInTags().stream()
                        .map(TaskTag::getFormattedTag)
                        .collect(Collectors.toList()))
                .customTags(task.getCustomTags())
                .estimatedHours(task.getEstimatedHours())
                .build();
    }
}
