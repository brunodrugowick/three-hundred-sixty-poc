package dev.drugowick.threehundredsixty.dto;

import dev.drugowick.threehundredsixty.domain.entity.Employee;
import dev.drugowick.threehundredsixty.validation.ImprovementNotBlankIfEvaluationIsNegative;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Size(min = 1, max = 60, message = "Categoria deve conter no mínimo 1 e no máximo 60 caracteres")
    private String category;

    @NotBlank
    @Size(min = 1, max = 60, message = "Título deve conter no mínimo 1 e no máximo 60 caracteres")
    private String title;

    @NotBlank
    @Size(min = 1, max = 120, message = "Descrição deve conter no mínimo 1 e no máximo 120 caracteres")
    private String description;

    @NotBlank
    private String evaluation;

    @NotBlank
    @Size(min = 3, max = 2000, message = "Exemplo deve conter no mínimo 3 e no máximo 2000 caracteres")
    private String example;

    @Size(min = 3, max = 2000, message = "Sugestão de Melhoria deve conter no mínimo 3 e no máximo 2000 caracteres")
    private String improvement;
}
