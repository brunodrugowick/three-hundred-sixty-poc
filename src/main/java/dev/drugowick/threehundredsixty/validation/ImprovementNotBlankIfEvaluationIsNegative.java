package dev.drugowick.threehundredsixty.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { ImprovementNotBlankIfEvaluationIsNegativeValidator.class })
public @interface ImprovementNotBlankIfEvaluationIsNegative {

    String message() default "deve ser preenchido se avaliação é negativa";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldEvaluation();
    String fieldImprovement();
    String[] negativeEvaluationValues();
}
