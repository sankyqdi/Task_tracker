package app.dto;

import app.model.Task;
import app.model.TaskTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskShowDTO {

    private Long id;

    private String name;

    private String body;

    private byte importanceLevel;

    private String stage;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    private LocalDate dueDate;

    private boolean isCompleted;

    private List<String> builtInTags;

    private List<String> customTags;

    private Double estimatedHours;

    public static TaskShowDTO from(Task task) {

        return TaskShowDTO.builder()
                .id(task.getId())
                .name(task.getName())
                .body(task.getBody())
                .importanceLevel(task.getImportanceLevel())
                .stage(task.getStage())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .dueDate(task.getDueDate())
                .isCompleted(task.isCompleted())
                .builtInTags(task.getBuiltInTags().stream()
                        .map(TaskTag::getFormattedTag)
                        .collect(Collectors.toList()))
                .customTags(task.getCustomTags())
                .estimatedHours(task.getEstimatedHours())
                .build();

    }
}
