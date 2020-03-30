package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Feedback relates a user to an employee being evaluated.
 */
@Entity
@Data
@NoArgsConstructor
public class Feedback {

    @EmbeddedId
    private Key key = new Key();

    @ManyToOne
    @MapsId("evaluatorId")
    private Employee evaluator;

    @ManyToOne
    @MapsId("evaluatedId")
    private Employee evaluated;

    @Embeddable
    @Data
    public static class Key implements Serializable {
        private Long evaluatorId;
        private Long evaluatedId;
    }

    @Enumerated(value = EnumType.STRING)
    private FeedbackState state = FeedbackState.NOT_STARTED;

}
