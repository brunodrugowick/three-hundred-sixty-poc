package dev.drugowick.threehundredsixty.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * A FeedbackQuestion is a question already designated to an employee. The set of questions of an employee is what
 * every user have to answer to have a complete feedback in place.
 *
 * There should be a routine in place to create and update the feedback questions database. From time to time you want
 * to update these questions.
 */
@Entity
@Data
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "evaluator_id", nullable = false)
    private Employee evaluator;

    @ManyToOne
    @JoinColumn(name = "evaluated_id", nullable = false)
    private Employee evaluated;

    @NotBlank
    @Size(min = 1, max = 60, message = "Categoria deve conter no mínimo 1 e no máximo 60 caracteres")
    private String category;

    @Size(min = 1, max = 60, message = "Título deve conter no mínimo 1 e no máximo 60 caracteres")
    private String title;

    @NotBlank
    @Size(min = 1, max = 120, message = "Descrição deve conter no mínimo 1 e no máximo 120 caracteres")
    private String description;

    private String evaluation;

    @Size(min = 3, max = 2000, message = "Exemplo deve conter no mínimo 3 e no máximo 2000 caracteres")
    private String example;

    @Size(min = 3, max = 2000, message = "Sugestão de Melhoria deve conter no mínimo 3 e no máximo 2000 caracteres")
    private String improvement;

    public Question(String category, String title, String description, Employee evaluated, Employee evaluator) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.evaluated = evaluated;
        this.evaluator = evaluator;
    }

    @JsonIgnore
    @Formula("CASE" +
            " WHEN evaluation = 'Não atende' THEN 1" +
            " WHEN evaluation = 'Atende parcialmente' THEN 2" +
            " WHEN evaluation = 'Atende' THEN 3" +
            " WHEN evaluation = 'Excede expectativas' THEN 4" +
            " ELSE 0 " +
            "END")
    private Integer evaluationValue;

    //TODO default toString methods from Lombok causes overflow
}
