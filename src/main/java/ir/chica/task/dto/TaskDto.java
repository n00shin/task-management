package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.chica.task.enums.TaskTypeEnum;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Date;

public record TaskDto(

        @NotBlank
        @JsonProperty("taskType") TaskTypeEnum taskType,
        @NotBlank
        @JsonProperty("name") String name,
        @NotBlank
        @JsonProperty("createdAt") LocalDateTime createdAt,
        @NotBlank
        @JsonProperty("updatedAt") LocalDateTime updatedAt,
        @NotBlank
        @JsonProperty("deletedAt") LocalDateTime deletedAt,
        @NotBlank
        @JsonProperty("done") boolean done)
{

}
