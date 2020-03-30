package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private String category;
    private String title;
    private String description;

    private String evaluation;
    private String example;
    private String improvement;

    public Question(String category, String title, String description, Employee evaluated, Employee evaluator) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.evaluated = evaluated;
        this.evaluator = evaluator;
    }

    //TODO default toString methods from Lombok causes overflow
}
