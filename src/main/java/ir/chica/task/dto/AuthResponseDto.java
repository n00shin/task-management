package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record AuthResponseDto(@NotBlank
                             @JsonProperty("username")
                             String token) {
}

