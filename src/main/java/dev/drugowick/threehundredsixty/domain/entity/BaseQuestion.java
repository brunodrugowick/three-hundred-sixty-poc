package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * A base question is a question that applies to a category. A category is how a question will eventually be linked to
 * an employee. this serves as a database of questions.
 */
@Entity(name = "base_question")
@Data
@NoArgsConstructor
public class BaseQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Denotes the position to which to apply the question. For example, "Systems Analyst".
     */
    @NotBlank
    @Size(min = 1, max = 60, message = "Cargo deve conter no mínimo 1 e no máximo 60 caracteres")
    private String position;

    @NotBlank
    @Size(min = 1, max = 60, message = "Categoria deve conter no mínimo 1 e no máximo 60 caracteres")
    private String category;

    @NotBlank
    @Size(min = 1, max = 120, message = "Descrição deve conter no mínimo 1 e no máximo 120 caracteres")
    private String description;

    //TODO default toString methods from Lombok causes overflow
}
