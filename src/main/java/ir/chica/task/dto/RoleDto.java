package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record RoleDto(
        @NotBlank
        @JsonProperty("main") String main,
        @NotBlank
        @JsonProperty("category") String category,
        @NotBlank
        @JsonProperty("roleName") String roleName) {
}
