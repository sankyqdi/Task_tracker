package app.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TaskDeletedDTO {

    private Long id;

    private String name;


    public static TaskDeletedDTO from(TaskShowDTO taskShowDTO) {

        return TaskDeletedDTO.builder()
                .id(taskShowDTO.getId())
                .name(taskShowDTO.getName())
                .build();
    }
}

