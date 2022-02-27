package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public record IdsDto(
        @NotNull
        @JsonProperty("id") List<Long> ids
) {
}
