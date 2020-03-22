package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String position;

    private String category;
    private String description;

    //TODO default toString methods from Lombok causes overflow
}
