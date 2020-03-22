package dev.drugowick.threehundredsixty.domain.entity;

import dev.drugowick.threehundredsixty.domain.entity.security.User;
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
public class FeedbackQuestion {

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

    public FeedbackQuestion(String category, String title, String description, Employee employee, User user) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.employee = employee;
        this.user = user;
    }

    //TODO default toString methods from Lombok causes overflow
}
