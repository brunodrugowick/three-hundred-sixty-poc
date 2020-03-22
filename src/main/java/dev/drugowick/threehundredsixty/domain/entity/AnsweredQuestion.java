package dev.drugowick.threehundredsixty.domain.entity;

import dev.drugowick.threehundredsixty.domain.entity.security.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * When a user answers a feedback, it's creating its own answers to FeedbackQuestions, so here the user is referenced.
 */
@Entity
@Data
@NoArgsConstructor
public class AnsweredQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String category;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "evaluated_id", nullable = false)
    private Employee employee;

    private String evaluation;
    private String example;
    private String improvement;

    //TODO default toString methods from Lombok causes overflow
}
