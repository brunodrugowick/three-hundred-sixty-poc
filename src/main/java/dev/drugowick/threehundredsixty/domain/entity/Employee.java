package dev.drugowick.threehundredsixty.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Employee {

    @Id
    private Long id;

    private String name;
    private String position;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY)
    private List<FeedbackQuestion> feedbackQuestions;

    //TODO default toString methods from Lombok causes overflow
}
