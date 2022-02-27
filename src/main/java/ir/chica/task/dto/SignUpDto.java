package ir.chica.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public record SignUpDto(
        @NotBlank
        @JsonProperty("username")
        String username,
        @NotBlank
        @Email
        @JsonProperty("email")
        String email,
        @NotBlank
        @JsonProperty("password")
        String password) {


}