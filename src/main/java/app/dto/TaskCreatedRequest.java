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

@Setter
@Getter
@AllArgsConstructor
@Builder
public class TaskCreatedRequest {

    private String name;

    private String body;

    private byte importanceLevel;

    private byte priority;

    private String stage;

    private LocalDate dueDate;

    private List<String> builtInTags;

    private List<String> customTags;

}


