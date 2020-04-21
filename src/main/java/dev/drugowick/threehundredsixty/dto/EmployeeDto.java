package dev.drugowick.threehundredsixty.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 120, message = "Nome deve conter no mínimo 1 e no máximo 120 caracteres")
    private String name;

    @NotBlank
    @Size(min = 1, max = 60, message = "Cargo deve conter no mínimo 1 e no máximo 60 caracteres")
    private String position;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String roles;

    private boolean enabled = true;
}
