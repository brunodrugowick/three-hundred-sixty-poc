package dev.drugowick.threehundredsixty.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { NewPasswordFieldsMustMatchValidator.class })
public @interface NewPasswordFieldsMustMatch {

    String message() default "A senha e a confirmação de senha devem ser iguais";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldNewPassword();
    String fieldNewPasswordConfirmation();
}
