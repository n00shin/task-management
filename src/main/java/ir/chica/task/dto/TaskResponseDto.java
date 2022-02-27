package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ir.chica.task.enums.TaskTypeEnum;
import ir.chica.task.model.Task;

import java.time.LocalDateTime;

public record TaskResponseDto(
        String name,
        TaskTypeEnum taskType,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        LocalDateTime createAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        LocalDateTime updateAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
        LocalDateTime deletedAt,
        boolean done)  {
    public TaskResponseDto(Task task) {
        this(task.getName(),task.getTaskType(),task.getCreatedAt(),task.getUpdatedAt()
                , task.getDeletedAt(),task.isDone());
    }
}
