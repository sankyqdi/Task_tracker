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
public class TaskUpdatedDTO {

    private String name;

    private String body;

    private String stage;

    private byte priority;

    private LocalDate dueDate;

    private LocalDate updatedAt;

    private boolean isCompleted;

    private List<String> builtInTags;

    private List<String> customTags;

    private Double estimatedHours;

    public static TaskUpdatedDTO from(Task task) {

        return TaskUpdatedDTO.builder()
                .name(task.getName())
                .body(task.getBody())
                .stage(task.getStage())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
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
