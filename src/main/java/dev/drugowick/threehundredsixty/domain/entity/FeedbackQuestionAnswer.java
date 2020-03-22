package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FeedbackQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    private FeedbackQuestion question;

    private String evaluation;
    private String example;
    private String improvement;

    public FeedbackQuestionAnswer(FeedbackQuestion question) {
        this.question = question;
    }

    //TODO default toString methods from Lombok causes overflow
}
