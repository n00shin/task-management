package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ir.chica.task.validator.PhoneNumber;
import ir.chica.task.validator.Sex;
import ir.chica.task.validator.ValidEmail;

import javax.validation.constraints.NotBlank;

public record UserDto(
        @NotBlank
        @JsonProperty("username") String username,
        @NotBlank
        @JsonProperty("password") String password,
        @NotBlank
        @ValidEmail
        @JsonProperty("email") String email,
        @NotBlank
        @PhoneNumber
        @JsonProperty("phoneNumber") String phoneNumber,
        @NotBlank
        @Sex
        @JsonProperty("sex") String sex) {
}
