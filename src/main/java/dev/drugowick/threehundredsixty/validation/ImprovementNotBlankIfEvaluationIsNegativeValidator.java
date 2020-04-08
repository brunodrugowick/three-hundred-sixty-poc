package dev.drugowick.threehundredsixty.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class ImprovementNotBlankIfEvaluationIsNegativeValidator implements ConstraintValidator<ImprovementNotBlankIfEvaluationIsNegative, Object> {

    private String fieldEvaluation;
    private String fieldImprovement;
    private String[] negativeEvaluationValues;

    @Override
    public void initialize(ImprovementNotBlankIfEvaluationIsNegative constraintAnnotation) {
        this.fieldEvaluation = constraintAnnotation.fieldEvaluation();
        this.negativeEvaluationValues = constraintAnnotation.negativeEvaluationValues();
        this.fieldImprovement = constraintAnnotation.fieldImprovement();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        try {
            String improvement = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(o.getClass(), this.fieldImprovement))
                    .getReadMethod().invoke(o);
            if (improvement == null || improvement.trim().isEmpty()) {
                String evaluation = (String) Objects.requireNonNull(BeanUtils.getPropertyDescriptor(o.getClass(), this.fieldEvaluation))
                        .getReadMethod().invoke(o);
                for (String negativeValue : negativeEvaluationValues) {
                    if (evaluation.equals(negativeValue)) {
                        isValid = false;
                        break;
                    }
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ValidationException(e);
        }

        return isValid;
    }
}
