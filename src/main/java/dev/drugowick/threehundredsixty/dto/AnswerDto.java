package dev.drugowick.threehundredsixty.dto;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.validation.ImprovementNotBlankIfEvaluationIsNegative;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * This class gets a custom annotation to ensure improvement is not blank when evaluation is negative.
 */
@Getter
@Setter
@ImprovementNotBlankIfEvaluationIsNegative(
        fieldEvaluation = "evaluation",
        fieldImprovement = "improvement",
        negativeEvaluationValues = {"Não atende", "Atende parcialmente"},
        message = "{message.custom.emptyImprovement}")
public class AnswerDto {
    private Long id;

    private Employee evaluator;
    private Employee evaluated;

    @NotBlank
    private String category;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String evaluation;

    @NotBlank
    private String example;

    private String improvement;
}
