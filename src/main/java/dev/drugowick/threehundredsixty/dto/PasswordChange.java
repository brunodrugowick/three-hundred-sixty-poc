package dev.drugowick.threehundredsixty.dto;

import dev.drugowick.threehundredsixty.validation.NewPasswordFieldsMustMatch;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@NewPasswordFieldsMustMatch(fieldNewPassword = "newPassword", fieldNewPasswordConfirmation = "newPasswordConfirmation")
public class PasswordChange {

    private String currentPassword;

    @NotBlank
    @Size(min = 4, max = 60, message = "Senha deve conter no mínimo 4 e no máximo 60 caracteres")
    private String newPassword;

    private String newPasswordConfirmation;
}
