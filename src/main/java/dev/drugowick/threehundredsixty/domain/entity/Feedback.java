package dev.drugowick.threehundredsixty.domain.entity;

import dev.drugowick.threehundredsixty.domain.entity.security.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * A Feedback relates a user to an employee being evaluated.
 */
@Entity
@Data
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(value = EnumType.STRING)
    private FeedbackState state = FeedbackState.NOT_STARTED;

    //TODO default toString methods from Lombok causes overflow
}
